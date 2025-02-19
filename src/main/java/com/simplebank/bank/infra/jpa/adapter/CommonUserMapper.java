package com.simplebank.bank.infra.jpa.adapter;

import com.simplebank.bank.domain.exception.MapperException;
import com.simplebank.bank.domain.model.User.BusinessUser;
import com.simplebank.bank.domain.model.User.ClientUser;
import com.simplebank.bank.domain.model.User.CommonUser;
import com.simplebank.bank.infra.jpa.entity.BusinessUserJpaEntity;
import com.simplebank.bank.infra.jpa.entity.ClientUserJpaEntity;
import com.simplebank.bank.infra.jpa.entity.CommonUserJpaEntity;

public class CommonUserMapper {
    public CommonUser toModel(CommonUserJpaEntity u) throws MapperException {
        return mapToConcreteModel(u);
    }

    public CommonUserJpaEntity toEntity(CommonUser u) throws MapperException {
        return mapToConcreteEntity(u);
    }

    private CommonUser mapToConcreteModel(CommonUserJpaEntity entity) throws MapperException {
        if(entity instanceof BusinessUserJpaEntity u) {
            return new BusinessUser(u.getId(), u.getName(), u.getEmail(), "", u.getCnpj());
        }

        if(entity instanceof ClientUserJpaEntity u) {
            return new ClientUser(u.getId(), u.getName(), u.getEmail(), "", u.getCpf());
        }

        throw new MapperException("The entity is not a user");
    }

    private CommonUserJpaEntity mapToConcreteEntity(CommonUser model) throws MapperException {
        if(model instanceof BusinessUser u) {
            return BusinessUserJpaEntity
                    .builder()
                    .id(u.getId())
                    .name(u.getName())
                    .email(u.getEmail())
                    .cnpj(u.getCnpj())
                    .build();
        }

        if(model instanceof ClientUser u) {
            return ClientUserJpaEntity
                    .builder()
                    .id(u.getId())
                    .name(u.getName())
                    .email(u.getEmail())
                    .cpf(u.getCpf())
                    .build();
        }

        throw new MapperException("The model is not a user");
    }
}
