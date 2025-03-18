package com.simplebank.bank.usecases;

import com.simplebank.bank.data.gateways.AccountRepositoryGateway;
import com.simplebank.bank.data.gateways.TransactionRepositoryGateway;
import com.simplebank.bank.domain.exceptions.AccountWithoutBalanceException;
import com.simplebank.bank.domain.exceptions.InvalidAmountException;
import com.simplebank.bank.domain.exceptions.UseCaseException;
import com.simplebank.bank.domain.models.Account.BusinessAccount;
import com.simplebank.bank.domain.models.Account.ClientAccount;
import com.simplebank.bank.domain.models.Transaction.Transaction;
import com.simplebank.bank.usecases.ports.InputValidator;
import com.simplebank.bank.services.TransferAuthService;
import com.simplebank.bank.usecases.ports.TransferDTORequest;
import com.simplebank.bank.usecases.ports.TransferDTOResponse;
import com.simplebank.bank.usecases.ports.TransferNotificationDTO;
import com.simplebank.bank.usecases.ports.TransferNotificationSender;
import org.springframework.transaction.annotation.Transactional;

public class Transfer implements UseCase<TransferDTORequest, TransferDTOResponse>
{
  private final AccountRepositoryGateway repository;
  private final TransactionRepositoryGateway transactionRepository;
  private final TransferAuthService transferAuthService;
  private final TransferNotificationSender notificationSender;
  private final InputValidator<TransferDTORequest> validator;

  public Transfer(AccountRepositoryGateway repository,
                  TransactionRepositoryGateway transactionRepository,
                  TransferAuthService transferAuthService,
                  TransferNotificationSender notificationSender,
                  InputValidator<TransferDTORequest> validator)
  {
    this.repository = repository;
    this.transactionRepository = transactionRepository;
    this.transferAuthService = transferAuthService;
    this.notificationSender = notificationSender;
    this.validator = validator;
  }

  @Override
  @Transactional
  public TransferDTOResponse execute(TransferDTORequest dto) throws UseCaseException
  {
    try
    {
      var violations = validator.validate(dto);

      if (!violations.isEmpty())
      {
        throw new UseCaseException("Invalid transfer input", violations);
      }

      var rawPayer = repository.find(dto.payerId());

      if (rawPayer == null)
      {
        throw new UseCaseException("Invalid payerId");
      }

      if (rawPayer instanceof BusinessAccount)
      {
        throw new UseCaseException("A business user can't make transfers");
      }

      if (!transferAuthService.authorize())
      {
        throw new UseCaseException("The transfer was not authorized");
      }

      var payee = repository.find(dto.payeeId());

      if (payee == null)
      {
        throw new UseCaseException("Invalid payeeId");
      }

      var payer = (ClientAccount) rawPayer;

      payer.transfer(dto.amount(), payee);

      var updatedPayer = repository.update(payer);
      var updatedPayee = repository.update(payee);

      var resultTransaction =
          transactionRepository.save(new Transaction(0, updatedPayer, updatedPayee, dto.amount()));

      notificationSender.send(new TransferNotificationDTO(updatedPayer.getUser().getEmail(),
          updatedPayee.getUser().getEmail(), resultTransaction.getAmount()));

      return new TransferDTOResponse(updatedPayer.getBalance());
    } catch (InvalidAmountException | AccountWithoutBalanceException e)
    {
      throw new UseCaseException(e.getMessage());
    }
  }
}
