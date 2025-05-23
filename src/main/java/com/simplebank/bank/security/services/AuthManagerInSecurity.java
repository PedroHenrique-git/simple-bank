package com.simplebank.bank.security.services;

import com.simplebank.bank.domain.Constants;
import com.simplebank.bank.domain.exceptions.ForbiddenException;
import com.simplebank.bank.domain.exceptions.InvalidCredentialsException;
import com.simplebank.bank.domain.exceptions.InvalidToken;
import com.simplebank.bank.domain.exceptions.UnauthorizedException;
import com.simplebank.bank.domain.models.User.User;
import com.simplebank.bank.infra.jpa.entities.UserEntity;
import com.simplebank.bank.infra.jpa.mappers.UserEntityMapper;
import com.simplebank.bank.infra.jpa.repositories.UserRepository;
import com.simplebank.bank.usecases.ports.AuthAuthenticatedUserDTOResponse;
import com.simplebank.bank.usecases.ports.AuthLoginDTORequest;
import com.simplebank.bank.usecases.ports.AuthManager;
import com.simplebank.bank.usecases.ports.AuthenticationDTO;
import com.simplebank.bank.usecases.ports.RefreshAuthDTORequest;
import com.simplebank.bank.usecases.ports.RefreshAuthDTOResponse;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthManagerInSecurity implements AuthManager
{
  private final AuthenticationManager authenticationManager;
  private final JWTService jwtService;
  private final UserEntityMapper mapper;
  private final UserRepository repository;

  public AuthManagerInSecurity(AuthenticationManager authenticationManager, UserEntityMapper mapper,
                               JWTService jwtService, UserRepository repository)
  {
    this.authenticationManager = authenticationManager;
    this.mapper = mapper;
    this.jwtService = jwtService;
    this.repository = repository;
  }

  @Override
  public AuthenticationDTO authenticate(AuthLoginDTORequest dto) throws InvalidCredentialsException
  {
    try
    {
      var emailPassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
      var auth = this.authenticationManager.authenticate(emailPassword);
      var user = (UserEntity) auth.getPrincipal();

      var commonToken =
          jwtService.generateToken(user, TokenType.AUTH, Constants.AUTH_TOKEN_EXPIRATION)
              .orElseThrow();

      var refreshToken =
          jwtService.generateToken(user, TokenType.REFRESH, Constants.REFRESH_TOKEN_EXPIRATION)
              .orElseThrow();

      return new AuthenticationDTO(commonToken, refreshToken,
          new AuthAuthenticatedUserDTOResponse(user.getId(), user.getName(), user.getEmail()));
    } catch (Exception e)
    {
      log.error("[AUTH MANAGER ERROR]: {}", e.toString());

      throw new InvalidCredentialsException("Invalid email or password");
    }
  }

  @Override
  public boolean isAuthorized(long userId) throws ForbiddenException
  {
    var authenticatedUser = getAuthenticatedUser();

    if (authenticatedUser.getId() == userId)
    {
      return true;
    }

    throw new ForbiddenException();
  }

  @Override
  public User getAuthenticatedUser()
  {
    var userEntity = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    return mapper.toModel((UserEntity) userEntity);
  }

  @Override
  public boolean logout()
  {
    SecurityContextHolder.clearContext();

    return true;
  }

  @Override
  public RefreshAuthDTOResponse refreshAuthentication(RefreshAuthDTORequest dto)
      throws InvalidToken, UnauthorizedException
  {
    var payload = jwtService.getPayload(dto.refreshToken())
        .orElseThrow(() -> new UnauthorizedException("Unauthorized"));

    if (Objects.equals(payload.type().value(), TokenType.AUTH.value()))
    {
      throw new InvalidToken("Invalid refresh token");
    }

    var userEntity = repository.findById(payload.userId())
        .orElseThrow(() -> new UnauthorizedException("Unauthorized"));

    var authToken =
        jwtService.generateToken(userEntity, TokenType.AUTH, Constants.AUTH_TOKEN_EXPIRATION)
            .orElseThrow();

    return new RefreshAuthDTOResponse(authToken);
  }
}
