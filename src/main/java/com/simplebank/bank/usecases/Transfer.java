package com.simplebank.bank.usecases;

import com.simplebank.bank.data.gateways.AccountRepositoryGateway;
import com.simplebank.bank.data.gateways.TransactionRepositoryGateway;
import com.simplebank.bank.domain.exceptions.AccountWithoutBalanceException;
import com.simplebank.bank.domain.exceptions.InvalidAmountException;
import com.simplebank.bank.domain.exceptions.UseCaseException;
import com.simplebank.bank.domain.models.Account.BusinessAccount;
import com.simplebank.bank.domain.models.Account.ClientAccount;
import com.simplebank.bank.domain.models.Transaction.Transaction;
import com.simplebank.bank.infra.notification.NotificationSender;
import com.simplebank.bank.usecases.ports.TransferAuthService;
import com.simplebank.bank.usecases.ports.TransferDTORequest;
import com.simplebank.bank.usecases.ports.TransferDTOResponse;
import org.springframework.transaction.annotation.Transactional;

public class Transfer implements UseCase<TransferDTORequest, TransferDTOResponse>
{
  private final AccountRepositoryGateway repository;
  private final TransactionRepositoryGateway transactionRepository;
  private final TransferAuthService transferAuthService;
  private final NotificationSender notificationSender;

  public Transfer(AccountRepositoryGateway repository,
                  TransactionRepositoryGateway transactionRepository,
                  TransferAuthService transferAuthService,
                  NotificationSender notificationSender)
  {
    this.repository = repository;
    this.transactionRepository = transactionRepository;
    this.transferAuthService = transferAuthService;
    this.notificationSender = notificationSender;
  }

  @Override
  @Transactional
  public TransferDTOResponse execute(TransferDTORequest dto) throws UseCaseException
  {
    try
    {
      var rawPayer = repository.find(dto.payerId());

      if (rawPayer instanceof BusinessAccount)
      {
        throw new UseCaseException("A business user can't make transfers");
      }

      //if (!transferAuthService.authorize())
      //{
      //throw new UseCaseException("The transfer was not authorized");
      //}

      var payee = repository.find(dto.payeeId());
      var payer = (ClientAccount) rawPayer;

      payer.transfer(dto.value(), payee);

      var updatedPayer = repository.update(payer);
      var updatedPayee = repository.update(payee);

      transactionRepository.save(new Transaction(0, updatedPayer, updatedPayee, dto.value()));

      notificationSender.send(payer.getUser());

      return new TransferDTOResponse(payer.getBalance());
    } catch (InvalidAmountException | AccountWithoutBalanceException e)
    {
      throw new UseCaseException(e.getMessage());
    }
  }
}
