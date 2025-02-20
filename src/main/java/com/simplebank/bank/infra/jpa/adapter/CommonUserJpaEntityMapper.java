package com.simplebank.bank.infra.jpa.adapter;

import com.simplebank.bank.domain.model.User.BusinessUser;
import com.simplebank.bank.domain.model.User.ClientUser;
import com.simplebank.bank.domain.model.User.CommonUser;
import com.simplebank.bank.infra.jpa.entity.BusinessUserJpaEntity;
import com.simplebank.bank.infra.jpa.entity.ClientUserJpaEntity;
import com.simplebank.bank.infra.jpa.entity.CommonUserJpaEntity;

public class CommonUserJpaEntityMapper {
    public CommonUser toModel(CommonUserJpaEntity u) {
        return mapToConcreteModel(u);
    }

    public CommonUserJpaEntity toEntity(CommonUser u) {
        return mapToConcreteEntity(u);
    }

    private CommonUser mapToConcreteModel(CommonUserJpaEntity entity) {
        if(entity instanceof BusinessUserJpaEntity u) {
            return new BusinessUser(u.getId(), u.getName(), u.getEmail(), "", u.getCnpj());
        }

        if(entity instanceof ClientUserJpaEntity u) {
            return new ClientUser(u.getId(), u.getName(), u.getEmail(), "", u.getCpf());
        }

        return null;
    }

    private CommonUserJpaEntity mapToConcreteEntity(CommonUser model) {
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

        return null;
    }
}
