package com.simplebank.bank.security.services;

import com.simplebank.bank.infra.jpa.entities.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTService
{
  @Value("${jwt.secret}")
  private String key;

  @Value("${spring.application.name}")
  private String issuer;

  public String generateToken(UserEntity entity)
  {
    var uName = entity.getName();
    var uEmail = entity.getEmail();
    var uId = entity.getId();

    return Jwts
        .builder()
        .issuer(issuer)
        .subject(uName)
        .signWith(getSigningKey())
        .expiration(getExpiration())
        .issuedAt(new Date())
        .claim("email", uEmail)
        .claim("name", uName)
        .claim("id", uId)
        .compact();
  }

  private Date getExpiration()
  {
    var oneHour = 60 * 60;

    return Date.from(Instant.now().plusSeconds(oneHour));
  }

  private Key getSigningKey()
  {
    byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);

    return Keys.hmacShaKeyFor(keyBytes);
  }
}
