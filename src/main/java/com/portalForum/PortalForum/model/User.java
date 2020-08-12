package com.portalForum.PortalForum.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Builder
public class User {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long userId;

  private String username;

  private String password;

  private String email;

  private Instant created;

  private boolean enabled;
}
