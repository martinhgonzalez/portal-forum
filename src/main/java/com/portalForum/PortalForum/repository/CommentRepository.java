package com.portalForum.PortalForum.repository;

import com.portalForum.PortalForum.model.Comment;
import com.portalForum.PortalForum.model.Post;
import com.portalForum.PortalForum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  List<Comment> findByPost(Post post);

  List<Comment> findAllByUser(User user);
}
