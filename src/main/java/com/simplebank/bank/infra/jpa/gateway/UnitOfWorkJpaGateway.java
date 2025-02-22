package com.simplebank.bank.infra.jpa.gateway;

import com.simplebank.bank.data.gateway.UnitOfWorkGateway;
import org.springframework.transaction.annotation.Transactional;

public class UnitOfWorkJpaGateway implements UnitOfWorkGateway {
    @Override
    @Transactional
    public void execute(Callback fn) {
        fn.cb();
    }
}
