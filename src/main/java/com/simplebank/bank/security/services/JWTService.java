package com.simplebank.bank.security.services;

import com.simplebank.bank.infra.jpa.entities.UserEntity;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.AesKey;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTService
{
  private final String USER_ID = "userId";
  private final String TYPE = "type";

  @Value("${jwt.secret}")
  private String key;

  @Value("${spring.application.name}")
  private String issuer;

  public String generateToken(UserEntity user, TokenType type, float expiration)
  {
    try
    {
      JwtClaims claims = new JwtClaims();

      claims.setIssuer(issuer);
      claims.setAudience("client");
      claims.setExpirationTimeMinutesInTheFuture(expiration);
      claims.setGeneratedJwtId();
      claims.setIssuedAtToNow();
      claims.setNotBeforeMinutesInThePast(2);
      claims.setSubject(user.getName());
      claims.setClaim(USER_ID, user.getId());
      claims.setClaim(TYPE, type.value());

      JsonWebSignature jws = new JsonWebSignature();

      jws.setKey(getSigningKey());
      jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);
      jws.setPayload(claims.toJson());

      return jws.getCompactSerialization();
    } catch (JoseException e)
    {
      return "";
    }
  }

  public Payload getPayload(String jwt)
  {
    try
    {
      JwtConsumer jwtConsumer = new JwtConsumerBuilder()
          .setRequireExpirationTime()
          .setAllowedClockSkewInSeconds(30)
          .setRequireSubject()
          .setExpectedAudience("client")
          .setVerificationKey(getSigningKey())
          .build();

      JwtClaims jwtClaims = jwtConsumer.processToClaims(jwt);

      var uId = jwtClaims.getClaimValue(USER_ID, Long.class);
      var tType = jwtClaims.getClaimValue(TYPE, String.class);

      return new Payload(uId, TokenType.valueOf(tType));
    } catch (Exception e)
    {
      return null;
    }
  }

  private Date getExpiration()
  {
    var oneHour = 60 * 60;

    return Date.from(Instant.now().plusSeconds(oneHour));
  }

  private Key getSigningKey()
  {
    return new AesKey(key.getBytes());
  }

  public record Payload(long userId, TokenType type)
  {
  }
}
