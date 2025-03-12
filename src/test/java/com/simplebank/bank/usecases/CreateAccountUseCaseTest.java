package com.simplebank.bank.usecases;

import com.simplebank.bank.config.AccountConfig;
import com.simplebank.bank.config.CommonConfig;
import com.simplebank.bank.config.TransactionConfig;
import com.simplebank.bank.config.UserConfig;
import com.simplebank.bank.domain.exceptions.UseCaseException;
import com.simplebank.bank.usecases.ports.CreateAccountDTORequest;
import com.simplebank.bank.usecases.ports.CreateAccountDTOResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import(value = {UserConfig.class, AccountConfig.class, TransactionConfig.class,
    CommonConfig.class})
public class CreateAccountUseCaseTest
{
  @Autowired
  public UseCase<CreateAccountDTORequest, CreateAccountDTOResponse> usecase;

  @Test
  void testCreateAccountUseCase() throws UseCaseException
  {
    assertThrows(UseCaseException.class,
        () -> usecase.execute(new CreateAccountDTORequest("", "", "", "")));
    assertThrows(UseCaseException.class,
        () -> usecase.execute(new CreateAccountDTORequest("Pedro", "", "", "")));
    assertThrows(UseCaseException.class,
        () -> usecase.execute(new CreateAccountDTORequest("Pedro", "p@email.com", "AA!45aaa", "")));


    var response = usecase.execute(
        new CreateAccountDTORequest("Pedro", "p@email.com", "AA!45aaa", "111.111.111-11"));

    assertNotNull(response.id());
    assertEquals("p@email.com", response.email());
    assertEquals("Pedro", response.name());
  }
}
