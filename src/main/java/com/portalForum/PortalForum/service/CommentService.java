package com.portalForum.PortalForum.service;

import com.portalForum.PortalForum.dto.CommentDto;
import com.portalForum.PortalForum.mapper.CommentMapper;
import com.portalForum.PortalForum.model.Comment;
import com.portalForum.PortalForum.model.NotificationEmail;
import com.portalForum.PortalForum.model.Post;
import com.portalForum.PortalForum.model.User;
import com.portalForum.PortalForum.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

  private final PostService postService;
  private final AuthService authService;
  private final CommentRepository commentRepository;
  private final MailContentBuilder mailContentBuilder;
  private final MailService mailService;

  public void save(CommentDto commentDto) {
    Post post = postService.getPostById(commentDto.getPostId());
    Comment comment =
        CommentMapper.INSTANCE.fromDtoToEntity(commentDto, post, authService.getCurrentUser());
    commentRepository.save(comment);

    String message =
        mailContentBuilder.build(
            comment.getUser().getUsername() + " posted a comment on your post: " + post.getUrl());
    sendCommentNotification(message, post.getUser(), comment.getUser().getUsername());
  }

  private void sendCommentNotification(String message, User postOwner, String commenterUsername) {
    mailService.sendMail(
        new NotificationEmail(
            "Hi, "
                + postOwner.getUsername()
                + "! "
                + commenterUsername
                + " has Commented on your post",
            postOwner.getEmail(),
            message));
  }

  @Transactional(readOnly = true)
  public List<Comment> getAllCommentsByPost(Long postId) {
    Post post = postService.getPostById(postId);

    return commentRepository.findByPost(post);
  }

  @Transactional(readOnly = true)
  public List<Comment> getAllCommentsByUser(String username) {
    User user = authService.findUserByUsername(username);

    return commentRepository.findAllByUser(user);
  }
}
