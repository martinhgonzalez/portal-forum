package com.portalForum.PortalForum.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.portalForum.PortalForum.model.VoteType;
import com.portalForum.PortalForum.utils.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoteDto {
  @NotNull(message = ErrorMessage.NOT_NULL)
  private VoteType voteType;

  @NotNull(message = ErrorMessage.NOT_NULL)
  @Positive(message = ErrorMessage.NOT_NEGATIVE)
  private Long postId;
}
