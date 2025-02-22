package com.simplebank.bank.data.gateway;

public interface UnitOfWorkGateway {
    void execute(Callback fn);

    @FunctionalInterface
    interface Callback {
        void cb();
    }
}
