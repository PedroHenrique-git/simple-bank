package com.simplebank.bank.usecases.mapper;

import com.simplebank.bank.domain.factories.AccountFactoryMaker;
import com.simplebank.bank.domain.factories.UserFactoryMaker;
import com.simplebank.bank.domain.models.Account.Account;
import com.simplebank.bank.usecases.ports.AccountDTORequest;
import com.simplebank.bank.usecases.ports.AccountDTOResponse;
import java.util.List;

public class AccountDTOMapper
{
  private final AccountFactoryMaker accountFactoryMaker;
  private final UserFactoryMaker userFactoryMaker;

  public AccountDTOMapper(AccountFactoryMaker accountFactoryMaker,
                          UserFactoryMaker userFactoryMaker)
  {
    this.accountFactoryMaker = accountFactoryMaker;
    this.userFactoryMaker = userFactoryMaker;
  }

  public AccountDTOResponse toResponse(Account account)
  {
    return new AccountDTOResponse(account.getUser().getId(), account.getUser().getName(),
        account.getUser().getEmail());
  }

  public Account toAccount(AccountDTORequest dto)
  {
    var document = dto.document();
    var account = accountFactoryMaker.getFactory(document).make();
    var user = userFactoryMaker.getFactory(document).make();

    user.setName(dto.name());
    user.setEmail(dto.email());
    user.setPassword(dto.password());
    user.setDocument(document);

    account.setUser(user);
    account.setPayeeTransactions(List.of());
    account.setPayerTransactions(List.of());
    account.setBalance(0);

    return account;
  }
}
