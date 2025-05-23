package com.simplebank.bank.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simplebank.bank.infra.jpa.repositories.UserRepository;
import com.simplebank.bank.presentation.controllers.http.HttpStatus;
import com.simplebank.bank.security.services.JWTService;
import com.simplebank.bank.security.services.TokenType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
public class SecurityFilter extends OncePerRequestFilter
{
  private final UserRepository repository;
  private final JWTService jwtService;
  private final Map<String, List<String>> whiteList;

  public SecurityFilter(JWTService jwtService, UserRepository repository)
  {
    this.jwtService = jwtService;
    this.repository = repository;

    this.whiteList =
        Map.of("/api/v1/accounts", List.of("GET"),
            "/api/v1/auth/login", List.of("POST"),
            "/api/v1/auth/logout", List.of("GET"),
            "/api/v1/auth/refresh-token", List.of("GET"));
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException
  {
    try
    {
      if (shouldSkip(request))
      {
        filterChain.doFilter(request, response);

        return;
      }

      var token = recoveryToken(request);
      var payload = jwtService.getPayload(token).orElseThrow();
      var user = repository.findById(payload.userId()).orElse(null);

      if (user == null || Objects.equals(payload.type().value(), TokenType.REFRESH.value()))
      {
        sendUnauthorizedResponse(response);

        return;
      }

      var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

      SecurityContextHolder.getContext().setAuthentication(auth);

      filterChain.doFilter(request, response);
    } catch (Exception e)
    {
      log.info("[SECURITY FILTER ERROR]: {}", e.toString());

      sendUnauthorizedResponse(response);
    }
  }

  private String recoveryToken(HttpServletRequest request)
      throws ServletException, IOException
  {
    var authorization = request.getHeader("Authorization");

    if (authorization == null)
    {
      return null;
    }

    return authorization.replace("Bearer ", "");
  }

  private void sendUnauthorizedResponse(HttpServletResponse response)
      throws ServletException, IOException
  {
    ObjectMapper body = new ObjectMapper();

    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.getWriter().write(body.writeValueAsString(Map.of("message",
        "Unauthorized")));
  }

  private boolean shouldSkip(HttpServletRequest request)
  {
    var uri = request.getRequestURI();
    var method = request.getMethod();

    if (!whiteList.containsKey(uri))
    {
      return false;
    }

    return whiteList.get(uri).contains(method);
  }
}
