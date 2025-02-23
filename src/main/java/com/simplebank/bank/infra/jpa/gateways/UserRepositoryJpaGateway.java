package com.simplebank.bank.infra.jpa.gateways;

import com.simplebank.bank.data.gateways.UserRepositoryGateway;
import com.simplebank.bank.domain.models.User.User;
import com.simplebank.bank.infra.jpa.mappers.UserEntityMapper;
import com.simplebank.bank.infra.jpa.repositories.UserRepository;

public class UserRepositoryJpaGateway implements UserRepositoryGateway
{
  private final UserRepository repository;
  private final UserEntityMapper mapper;

  public UserRepositoryJpaGateway(UserRepository repository,
                                  UserEntityMapper mapper)
  {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  public User save(User user)
  {
    var entity = mapper.toEntity(user, false);
    var createdUser = repository.save(entity);

    return mapper.toModel(createdUser);
  }

  @Override
  public User update(long id, User newUser)
  {
    var user = find(id);

    if (user == null)
    {
      return null;
    }

    user.setEmail(newUser.getEmail());
    user.setName(newUser.getName());
    user.setPassword(newUser.getPassword());

    var entity = mapper.toEntity(user, true);
    var updatedUser = repository.save(entity);

    return mapper.toModel(updatedUser);
  }

  @Override
  public User find(long id)
  {
    var entity = repository.findById(id);

    return entity.map(mapper::toModel).orElse(null);
  }

  @Override
  public void delete(long id)
  {
    repository.deleteById(id);
  }
}
