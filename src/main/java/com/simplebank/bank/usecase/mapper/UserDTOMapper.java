package com.simplebank.bank.usecase.mapper;

import com.simplebank.bank.domain.model.User.BusinessUser;
import com.simplebank.bank.domain.model.User.ClientUser;
import com.simplebank.bank.domain.model.User.User;
import com.simplebank.bank.infra.RegexMap;
import com.simplebank.bank.usecase.ports.UserDTORequest;
import com.simplebank.bank.usecase.ports.UserDTOResponse;

public class UserDTOMapper
{
  public UserDTOResponse toResponse(User user)
  {
    return new UserDTOResponse(user.getId(), user.getName(), user.getEmail());
  }

  public User toUser(UserDTORequest dto)
  {
    var document = dto.document();

    if (document.matches(RegexMap.CPF))
    {
      return new ClientUser(0, dto.name(), dto.email(), dto.password(), document);
    }

    return new BusinessUser(0, dto.name(), dto.email(), dto.password(), document);
  }
}
