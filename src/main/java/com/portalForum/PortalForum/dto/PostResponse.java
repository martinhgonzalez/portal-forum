package com.portalForum.PortalForum.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {
  private Long id;
  private String postName;
  private String url;
  private String description;
  private String userName;
  private String portalName;
  private Integer voteCount;
  private Integer commentCount;
  private String duration;
  private boolean upVote;
  private boolean downVote;
}
