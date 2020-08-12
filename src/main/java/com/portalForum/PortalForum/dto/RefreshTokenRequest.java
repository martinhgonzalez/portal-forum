package com.portalForum.PortalForum.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.portalForum.PortalForum.utils.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshTokenRequest {
  @NotBlank(message = ErrorMessage.NOT_BLANK)
  @NotBlank
  private String refreshToken;

  @NotBlank(message = ErrorMessage.NOT_BLANK)
  @NotBlank
  private String username;
}
