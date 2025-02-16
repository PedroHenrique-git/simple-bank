package com.simplebank.bank;

import com.simplebank.bank.domain.model.Account.Account;
import com.simplebank.bank.domain.model.Account.BusinessAccount;
import com.simplebank.bank.domain.model.Account.ClientAccount;
import com.simplebank.bank.domain.model.User.Client;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}

}
