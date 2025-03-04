package com.simplebank.bank.encoders;

import com.simplebank.bank.infra.encoders.BcryptEncoder;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@DisplayName("Encoder tests")
public class EncoderTests
{
  @Test
  void testEncoder()
  {
    var password = "abc123";
    var encoder = new BcryptEncoder(new BCryptPasswordEncoder());

    assertNotEquals(password, encoder.encode(password));
    assertTrue(encoder.compare(password, encoder.encode(password)));
    assertFalse(encoder.compare(password + "a", encoder.encode(password)));
  }
}
