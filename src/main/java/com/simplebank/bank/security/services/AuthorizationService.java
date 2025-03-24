package com.simplebank.bank.security.services;

import com.simplebank.bank.infra.jpa.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AuthorizationService implements UserDetailsService
{
  private final UserRepository userRepository;

  public AuthorizationService(UserRepository userRepository)
  {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
  {
    var user = userRepository.findByEmail(email);

    if (user == null)
    {
      throw new UsernameNotFoundException("Invalid email");
    }

    return user;
  }
}
