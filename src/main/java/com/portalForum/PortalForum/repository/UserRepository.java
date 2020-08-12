package com.portalForum.PortalForum.repository;

import com.portalForum.PortalForum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

  @Modifying
  @Query(value = "UPDATE users u SET enabled=true WHERE  u.user_id= :id", nativeQuery = true)
  void enableUserById(@Param("id") Long id);

  Optional<User> findByUsername(String username);
}
