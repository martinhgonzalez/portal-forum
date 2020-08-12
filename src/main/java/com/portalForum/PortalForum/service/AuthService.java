package com.portalForum.PortalForum.service;

import com.portalForum.PortalForum.dto.AuthenticationResponse;
import com.portalForum.PortalForum.dto.LoginRequest;
import com.portalForum.PortalForum.dto.RefreshTokenRequest;
import com.portalForum.PortalForum.dto.RegisterRequest;
import com.portalForum.PortalForum.exception.NotFoundException;
import com.portalForum.PortalForum.exception.PortalForumException;
import com.portalForum.PortalForum.model.NotificationEmail;
import com.portalForum.PortalForum.model.RefreshToken;
import com.portalForum.PortalForum.model.User;
import com.portalForum.PortalForum.model.VerificationToken;
import com.portalForum.PortalForum.repository.UserRepository;
import com.portalForum.PortalForum.repository.VerificationTokenRepository;
import com.portalForum.PortalForum.utils.Constants;
import com.portalForum.PortalForum.utils.ErrorMessage;
import com.portalForum.PortalForum.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final VerificationTokenRepository verificationTokenRepository;
  private final MailService mailService;
  private final AuthenticationManager authenticationManager;
  private final UserDetailsServiceImpl userDetailsService;
  private final JwtUtils jwtUtils;
  private final RefreshTokenService refreshTokenService;

  public void signup(RegisterRequest registerRequest) {

    User newUser =
        User.builder()
            .username(registerRequest.getUsername())
            .email(registerRequest.getEmail())
            .password(passwordEncoder.encode(registerRequest.getPassword()))
            .created(Instant.now())
            .enabled(false)
            .build();

    userRepository.save(newUser);

    String token = generateVerificationToken(newUser);

    mailService.sendMail(
        NotificationEmail.builder()
            .subject(Constants.confirmationEmailSubject)
            .recipient(newUser.getEmail())
            .body(Constants.confirmationEmailBody + token)
            .build());
  }

  @Transactional(readOnly = true)
  public User getCurrentUser() {
    Object principal1222 = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    org.springframework.security.core.userdetails.User principal =
        (org.springframework.security.core.userdetails.User) principal1222;

    return userRepository
        .findByUsername(principal.getUsername())
        .orElseThrow(
            () ->
                new UsernameNotFoundException("User name not found - " + principal.getUsername()));
  }

  private String generateVerificationToken(User user) {
    String token = UUID.randomUUID().toString();
    VerificationToken verificationToken =
        VerificationToken.builder().token(token).user(user).build();

    verificationTokenRepository.save(verificationToken);

    return token;
  }

  public void verifyAccount(String token) {
    VerificationToken verificationToken =
        verificationTokenRepository
            .findByToken(token)
            .orElseThrow(() -> new PortalForumException(ErrorMessage.INVALID_TOKEN));

    userRepository.enableUserById(verificationToken.getUser().getUserId());
  }

  public AuthenticationResponse login(LoginRequest loginRequest) {

    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              loginRequest.getUsername(), loginRequest.getPassword()));
    } catch (BadCredentialsException e) {
      throw new BadCredentialsException(ErrorMessage.INVALID_USERNAME_OR_PASSWORD, e);
    }

    final UserDetails userDetails =
        userDetailsService.loadUserByUsername(loginRequest.getUsername());

    final String jwt = jwtUtils.generateToken(userDetails);

    return AuthenticationResponse.builder()
        .username(loginRequest.getUsername())
        .authenticationToken(jwt)
        .refreshToken(
            refreshTokenService.generateRefreshToken(loginRequest.getUsername()).getToken())
        .expiresAt(Instant.now().plusMillis(jwtUtils.getJwtExpirationInMillis()))
        .build();
  }

  public boolean isLoggedIn() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    return !(authentication instanceof AnonymousAuthenticationToken)
        && authentication.isAuthenticated();
  }

  public User findUserByUsername(String username) {

    return userRepository
        .findByUsername(username)
        .orElseThrow(() -> new NotFoundException(ErrorMessage.USERNAME_NOT_FOUND + " " + username));
  }

  public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {

    RefreshToken refreshToken =
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
    UserDetails userDetails =
        userDetailsService.loadUserByUsername(refreshTokenRequest.getUsername());

    User userInDb = userRepository.findById(refreshToken.getUserId()).get();

    if (!userInDb.getUsername().equalsIgnoreCase(userDetails.getUsername())) {
      throw new PortalForumException(ErrorMessage.USERNAME_NOT_MATCH);
    }

    String token = jwtUtils.generateToken(userDetails);

    return AuthenticationResponse.builder()
        .authenticationToken(token)
        .refreshToken(refreshTokenRequest.getRefreshToken())
        .expiresAt(Instant.now().plusMillis(jwtUtils.getJwtExpirationInMillis()))
        .username(refreshTokenRequest.getUsername())
        .build();
  }
}
