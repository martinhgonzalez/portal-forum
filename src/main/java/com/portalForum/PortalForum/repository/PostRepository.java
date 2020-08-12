package com.portalForum.PortalForum.repository;

import com.portalForum.PortalForum.model.Portal;
import com.portalForum.PortalForum.model.Post;
import com.portalForum.PortalForum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
  List<Post> findAllByPortal(Portal portal);

  List<Post> findByUser(User user);
}
