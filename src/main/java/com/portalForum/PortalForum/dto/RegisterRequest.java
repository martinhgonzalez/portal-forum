package com.portalForum.PortalForum.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.portalForum.PortalForum.utils.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
  @NotBlank(message = ErrorMessage.NOT_BLANK)
  @Email(message = ErrorMessage.INVALID_EMAIL)
  private String email;

  @NotBlank(message = ErrorMessage.NOT_BLANK)
  private String username;

  @NotBlank(message = ErrorMessage.NOT_BLANK)
  private String password;
}
