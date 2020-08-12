package com.portalForum.PortalForum.service;

import com.portalForum.PortalForum.exception.PortalForumException;
import com.portalForum.PortalForum.model.RefreshToken;
import com.portalForum.PortalForum.repository.RefreshTokenRepository;
import com.portalForum.PortalForum.utils.ErrorMessage;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
public class RefreshTokenService {

  private final RefreshTokenRepository refreshTokenRepository;
  private final AuthService authService;

  public RefreshTokenService(
      RefreshTokenRepository refreshTokenRepository, @Lazy AuthService authService) {
    this.refreshTokenRepository = refreshTokenRepository;
    this.authService = authService;
  }

  public RefreshToken generateRefreshToken(String username) {
    RefreshToken refreshToken =
        RefreshToken.builder()
            .userId(authService.findUserByUsername(username).getUserId())
            .token(UUID.randomUUID().toString())
            .createdDate(Instant.now())
            .build();

    return refreshTokenRepository.save(refreshToken);
  }

  @Transactional(readOnly = true)
  public RefreshToken validateRefreshToken(String token) {

    return refreshTokenRepository
        .findByToken(token)
        .orElseThrow(() -> new PortalForumException(ErrorMessage.INVALID_REFRESH_TOKEN));
  }

  public void deleteRefreshToken(String token) {
    refreshTokenRepository.deleteByToken(token);
  }
}
