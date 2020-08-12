package com.portalForum.PortalForum.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.portalForum.PortalForum.utils.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostRequest {
  @NotNull(message = ErrorMessage.NOT_NULL)
  @Positive(message = ErrorMessage.NOT_NEGATIVE)
  private Long portalId;

  @NotBlank(message = ErrorMessage.NOT_BLANK)
  private String postName;

  @NotBlank(message = ErrorMessage.NOT_BLANK)
  private String url;

  @NotBlank(message = ErrorMessage.NOT_BLANK)
  private String description;
}
