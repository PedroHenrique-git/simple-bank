package com.simplebank.bank.infra.jpa.mapper;

import com.simplebank.bank.domain.model.User.BusinessUser;
import com.simplebank.bank.domain.model.User.ClientUser;
import com.simplebank.bank.domain.model.User.User;
import com.simplebank.bank.infra.jpa.entity.BusinessUserEntity;
import com.simplebank.bank.infra.jpa.entity.ClientUserEntity;
import com.simplebank.bank.infra.jpa.entity.UserEntity;

public class UserEntityMapper {
    public User toModel(UserEntity u) {
        return mapToConcreteModel(u);
    }

    public UserEntity toEntity(User u) {
        return mapToConcreteEntity(u);
    }

    private User mapToConcreteModel(UserEntity entity) {
        if(entity instanceof BusinessUserEntity u) {
            return new BusinessUser(u.getId(), u.getName(), u.getEmail(), "", u.getCnpj());
        }

        if(entity instanceof ClientUserEntity u) {
            return new ClientUser(u.getId(), u.getName(), u.getEmail(), "", u.getCpf());
        }

        return null;
    }

    private UserEntity mapToConcreteEntity(User model) {
        if(model instanceof BusinessUser u) {
            return BusinessUserEntity
                    .builder()
                    .name(u.getName())
                    .email(u.getEmail())
                    .password(u.getPassword())
                    .cnpj(u.getCnpj())
                    .build();
        }

        if(model instanceof ClientUser u) {
            return ClientUserEntity
                    .builder()
                    .name(u.getName())
                    .email(u.getEmail())
                    .password(u.getPassword())
                    .cpf(u.getCpf())
                    .build();
        }

        return null;
    }
}
