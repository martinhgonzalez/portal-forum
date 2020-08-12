package com.portalForum.PortalForum.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.portalForum.PortalForum.utils.ErrorMessage;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.Instant;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
  @ApiModelProperty(readOnly = true)
  private Long id;

  @NotNull(message = ErrorMessage.NOT_NULL)
  @Positive(message = ErrorMessage.NOT_NEGATIVE)
  private Long postId;

  @ApiModelProperty(readOnly = true)
  private Instant createdDate;

  @NotBlank(message = ErrorMessage.NOT_BLANK)
  private String text;

  @ApiModelProperty(readOnly = true)
  private String userName;
}
