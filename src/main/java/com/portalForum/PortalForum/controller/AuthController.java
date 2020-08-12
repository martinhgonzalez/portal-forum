package com.portalForum.PortalForum.controller;

import com.portalForum.PortalForum.dto.AuthenticationResponse;
import com.portalForum.PortalForum.dto.LoginRequest;
import com.portalForum.PortalForum.dto.RefreshTokenRequest;
import com.portalForum.PortalForum.dto.RegisterRequest;
import com.portalForum.PortalForum.service.AuthService;
import com.portalForum.PortalForum.service.RefreshTokenService;
import com.portalForum.PortalForum.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService authService;
  private final RefreshTokenService refreshTokenService;

  @PostMapping("/signup")
  public ResponseEntity<String> signUp(@Valid @RequestBody RegisterRequest registerRequest) {
    authService.signup(registerRequest);

    return new ResponseEntity(Constants.REGISTRATION_SUCCESSFUL, HttpStatus.CREATED);
  }

  @GetMapping("/accountVerification/{token}")
  public ResponseEntity<String> verifyAccount(@PathVariable String token) {
    authService.verifyAccount(token);

    return ResponseEntity.ok(Constants.ACCOUNT_VERIFICATED);
  }

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> login(
      @Valid @RequestBody LoginRequest loginRequest) {

    return ResponseEntity.ok(authService.login(loginRequest));
  }

  @PostMapping("/refresh/token")
  public ResponseEntity<AuthenticationResponse> refreshTokens(
      @Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {

    return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout(
      @Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
    refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());

    return ResponseEntity.ok(Constants.REFRESH_TOKEN_DELETED);
  }
}
