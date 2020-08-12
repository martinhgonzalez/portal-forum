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

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PortalDto {
  @ApiModelProperty(readOnly = true)
  private Long id;

  @NotBlank(message = ErrorMessage.NOT_BLANK)
  private String name;

  @NotBlank(message = ErrorMessage.NOT_BLANK)
  private String description;

  @ApiModelProperty(readOnly = true)
  private Integer numberOfPosts;
}
