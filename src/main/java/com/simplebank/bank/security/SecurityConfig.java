package com.simplebank.bank.security;

import com.simplebank.bank.infra.jpa.repositories.UserRepository;
import com.simplebank.bank.presentation.controllers.ControllerOperation;
import com.simplebank.bank.presentation.controllers.LoginOperation;
import com.simplebank.bank.presentation.controllers.WebController;
import com.simplebank.bank.security.filters.SecurityFilter;
import com.simplebank.bank.security.services.AuthorizationService;
import com.simplebank.bank.usecases.Login;
import com.simplebank.bank.usecases.UseCase;
import com.simplebank.bank.usecases.ports.AuthLoginDTORequest;
import com.simplebank.bank.usecases.ports.AuthLoginDTOResponse;
import com.simplebank.bank.usecases.ports.AuthManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{
  private final SecurityFilter securityFilter;

  public SecurityConfig(SecurityFilter securityFilter)
  {
    this.securityFilter = securityFilter;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
  {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(session -> session.sessionCreationPolicy(
            SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/v1/accounts").permitAll()
            .anyRequest()
            .authenticated())
        .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder()
  {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider(AuthorizationService authorizationService)
  {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

    authProvider.setUserDetailsService(authorizationService);
    authProvider.setPasswordEncoder(bCryptPasswordEncoder());

    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration)
      throws Exception
  {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public AuthorizationService authorizationService(UserRepository repository)
  {
    return new AuthorizationService(repository);
  }

  @Bean
  public UseCase<AuthLoginDTORequest, AuthLoginDTOResponse> login(AuthManager manager)
  {
    return new Login(manager);
  }

  @Bean
  public ControllerOperation<AuthLoginDTOResponse, AuthLoginDTORequest> loginOperation(
      UseCase<AuthLoginDTORequest, AuthLoginDTOResponse> usecase)
  {
    return new LoginOperation(usecase);
  }

  @Bean
  public WebController<AuthLoginDTOResponse, AuthLoginDTORequest> loginController(
      ControllerOperation<AuthLoginDTOResponse, AuthLoginDTORequest> operation)
  {
    return new WebController<>(operation);
  }
}
