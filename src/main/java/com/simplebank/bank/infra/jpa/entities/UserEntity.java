package com.simplebank.bank.infra.jpa.entities;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.HashSet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "common_user")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class UserEntity implements UserDetails
{
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @Nonnull
  private String name;

  @Nonnull
  @Column(unique = true)
  private String email;

  @Nonnull
  private String password;

  @OneToOne(mappedBy = "user")
  private AccountEntity account;

  public abstract String getDocument();

  public abstract void setDocument(String document);

  @Override
  public String getUsername()
  {
    return this.email;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities()
  {
    return new HashSet<>();
  }

  @Override
  public boolean isAccountNonExpired()
  {
    return true;
  }

  @Override
  public boolean isAccountNonLocked()
  {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired()
  {
    return true;
  }

  @Override
  public boolean isEnabled()
  {
    return true;
  }
}
