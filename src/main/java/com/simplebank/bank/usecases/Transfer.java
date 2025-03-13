package com.simplebank.bank.usecases;

import com.simplebank.bank.data.gateways.AccountRepositoryGateway;
import com.simplebank.bank.domain.exceptions.AccountWithoutBalanceException;
import com.simplebank.bank.domain.exceptions.InvalidAmountException;
import com.simplebank.bank.domain.exceptions.UseCaseException;
import com.simplebank.bank.domain.models.Account.BusinessAccount;
import com.simplebank.bank.domain.models.Account.ClientAccount;
import com.simplebank.bank.usecases.ports.TransferAuthService;
import com.simplebank.bank.usecases.ports.TransferDTORequest;
import com.simplebank.bank.usecases.ports.TransferDTOResponse;
import org.springframework.transaction.annotation.Transactional;

public class Transfer implements UseCase<TransferDTORequest, TransferDTOResponse>
{
  private final AccountRepositoryGateway repository;
  private final TransferAuthService transferAuthService;

  public Transfer(AccountRepositoryGateway repository, TransferAuthService transferAuthService)
  {
    this.repository = repository;
    this.transferAuthService = transferAuthService;
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

      if (!transferAuthService.authorize())
      {
        throw new UseCaseException("The transfer can't be made");
      }

      var payee = repository.find(dto.payeeId());
      var payer = (ClientAccount) rawPayer;

      payer.transfer(dto.value(), payee);

      repository.update(payer);
      repository.update(payee);

      return new TransferDTOResponse(payer.getBalance());
    } catch (InvalidAmountException | AccountWithoutBalanceException e)
    {
      throw new UseCaseException(e.getMessage());
    }
  }
}
