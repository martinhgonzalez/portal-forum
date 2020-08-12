package com.portalForum.PortalForum.service;

import com.portalForum.PortalForum.exception.NotFoundException;
import com.portalForum.PortalForum.model.User;
import com.portalForum.PortalForum.repository.UserRepository;
import com.portalForum.PortalForum.utils.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static java.util.Collections.singletonList;

@Service
@RequiredArgsConstructor
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) {
    User user =
        userRepository
            .findByUsername(username)
            .orElseThrow(
                () -> new NotFoundException(ErrorMessage.USERNAME_NOT_FOUND + " " + username));

    return new org.springframework.security.core.userdetails.User(
        user.getUsername(),
        user.getPassword(),
        user.isEnabled(),
        true,
        true,
        true,
        getAuthorities("USER"));
  }

  private Collection<? extends GrantedAuthority> getAuthorities(String role) {

    return singletonList(new SimpleGrantedAuthority(role));
  }
}
