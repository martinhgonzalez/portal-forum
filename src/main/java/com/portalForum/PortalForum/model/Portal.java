package com.portalForum.PortalForum.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.Instant;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "portals")
@Builder
public class Portal {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long portalId;

  private String name;

  private String description;

  @OneToMany(mappedBy = "portal", fetch = LAZY)
  private List<Post> posts;

  private Instant createdDate;
}
