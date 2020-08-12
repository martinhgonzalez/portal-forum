package com.portalForum.PortalForum.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Instant;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts")
@Builder
public class Post {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long postId;

  private String postName;

  private String url;

  @Lob private String description;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "userId", referencedColumnName = "userId")
  private User user;

  private Instant createdDate;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "portalId", referencedColumnName = "portalId")
  private Portal portal;
}
