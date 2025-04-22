package com.simplebank.bank.usecases;

import com.simplebank.bank.data.gateways.AccountRepositoryGateway;
import com.simplebank.bank.domain.exceptions.ForbiddenException;
import com.simplebank.bank.domain.exceptions.UnauthorizedException;
import com.simplebank.bank.domain.exceptions.UseCaseException;
import com.simplebank.bank.usecases.mapper.TransactionDTOMapper;
import com.simplebank.bank.usecases.ports.AuthManager;
import com.simplebank.bank.usecases.ports.AuthenticatedUserAccountDTORequest;
import com.simplebank.bank.usecases.ports.AuthenticatedUserAccountDTOResponse;

public class AuthenticatedUserAccount
    implements UseCase<AuthenticatedUserAccountDTORequest, AuthenticatedUserAccountDTOResponse>
{
  private final AuthManager authManager;
  private final AccountRepositoryGateway repository;
  private final TransactionDTOMapper dtoMapper;

  public AuthenticatedUserAccount(AuthManager authManager, AccountRepositoryGateway repository,
                                  TransactionDTOMapper dtoMapper)
  {
    this.authManager = authManager;
    this.repository = repository;
    this.dtoMapper = dtoMapper;
  }

  @Override
  public AuthenticatedUserAccountDTOResponse execute(AuthenticatedUserAccountDTORequest data)
      throws UseCaseException, UnauthorizedException, ForbiddenException
  {
    var user = this.authManager.getAuthenticatedUser();
    var account = this.repository.findByUserId(user.getId());

    this.authManager.isAuthorized(account.getId());

    return new AuthenticatedUserAccountDTOResponse(account.getId(), account.getBalance(),
        dtoMapper.toResponse(account.getPayerTransactions()),
        dtoMapper.toResponse(account.getPayeeTransactions()));
  }
}
