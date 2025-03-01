package com.simplebank.bank.usecases.mapper;

import com.simplebank.bank.domain.factories.AccountFactoryMaker;
import com.simplebank.bank.domain.factories.UserFactoryMaker;
import com.simplebank.bank.domain.models.Account.Account;
import com.simplebank.bank.usecases.ports.CreateAccountDTORequest;
import com.simplebank.bank.usecases.ports.CreateAccountDTOResponse;
import java.util.List;

public class CreateAccountDTOMapper
{
  private final AccountFactoryMaker accountFactoryMaker;
  private final UserFactoryMaker userFactoryMaker;

  public CreateAccountDTOMapper(AccountFactoryMaker accountFactoryMaker,
                                UserFactoryMaker userFactoryMaker)
  {
    this.accountFactoryMaker = accountFactoryMaker;
    this.userFactoryMaker = userFactoryMaker;
  }

  public CreateAccountDTOResponse toResponse(Account account)
  {
    return new CreateAccountDTOResponse(account.getUser().getId(), account.getUser().getName(),
        account.getUser().getEmail());
  }

  public Account toAccount(CreateAccountDTORequest dto)
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
    account.setBalance(0.0);

    return account;
  }
}
