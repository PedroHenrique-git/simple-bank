package com.simplebank.bank.infra.jpa.mappers;

import com.simplebank.bank.domain.factories.UserFactoryMaker;
import com.simplebank.bank.domain.models.User.User;
import com.simplebank.bank.infra.jpa.entities.UserEntity;
import com.simplebank.bank.infra.jpa.factories.UserEntityFactoryMaker;

public class UserEntityMapper
{
  private final UserFactoryMaker userFactory;
  private final UserEntityFactoryMaker userEntityFactoryMaker;

  public UserEntityMapper(UserFactoryMaker userFactory,
                          UserEntityFactoryMaker userEntityFactoryMaker)
  {
    this.userFactory = userFactory;
    this.userEntityFactoryMaker = userEntityFactoryMaker;
  }

  public User toModel(UserEntity u)
  {
    return mapToConcreteModel(u);
  }

  public UserEntity toEntity(User u)
  {
    return mapToConcreteEntity(u);
  }

  private User mapToConcreteModel(UserEntity u)
  {
    var document = u.getDocument();
    var user = userFactory.getFactory(document).make();

    user.setId(u.getId());
    user.setName(u.getName());
    user.setEmail(u.getEmail());
    user.setPassword("");
    user.setDocument(document);

    return user;
  }

  private UserEntity mapToConcreteEntity(User u)
  {
    var document = u.getDocument();
    var user = userEntityFactoryMaker.getFactory(document).make();

    user.setName(u.getName());
    user.setEmail(u.getEmail());
    user.setPassword(u.getPassword());
    user.setDocument(document);

    return user;
  }
}
