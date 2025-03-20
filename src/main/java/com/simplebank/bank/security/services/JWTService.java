package com.simplebank.bank.security.services;

import com.simplebank.bank.infra.jpa.entities.UserEntity;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
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
  private final String USER = "user";
  private final String TYPE = "type";

  @Value("${jwt.secret}")
  private String key;

  @Value("${spring.application.name}")
  private String issuer;

  public String generateToken(UserEntity user, TokenType type, float expiration)
  {
    try
    {
      var uId = user.getId();
      var uEmail = user.getEmail();
      var uName = user.getName();

      JwtClaims claims = new JwtClaims();

      claims.setIssuer(issuer);
      claims.setAudience("client");
      claims.setExpirationTimeMinutesInTheFuture(expiration);
      claims.setGeneratedJwtId();
      claims.setIssuedAtToNow();
      claims.setNotBeforeMinutesInThePast(2);
      claims.setSubject(uName);
      claims.setClaim(USER, Map.of("id", uId.toString(), "email", uEmail, "name", uName));
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

      var user = jwtClaims.getClaimValue(USER, Map.class);

      var uId = (String) user.get("id");
      var uEmail = (String) user.get("email");
      var uName = (String) user.get("name");
      var tType = (String) jwtClaims.getClaimValue(TYPE);

      return new Payload(Long.valueOf(uId), uEmail, uName, TokenType.valueOf(tType));
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

  public record Payload(long id, String email, String name, TokenType type)
  {
  }
}
