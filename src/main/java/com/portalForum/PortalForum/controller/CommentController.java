package com.portalForum.PortalForum.controller;

import com.portalForum.PortalForum.dto.CommentDto;
import com.portalForum.PortalForum.service.CommentService;
import com.portalForum.PortalForum.utils.CommentUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments/")
public class CommentController {
  private final CommentService commentService;

  @PostMapping
  public ResponseEntity<Void> createComment(@Valid @RequestBody CommentDto commentDto) {
    commentService.save(commentDto);

    return new ResponseEntity<>(CREATED);
  }

  @GetMapping("/by-post/{postId}")
  public ResponseEntity<List<CommentDto>> getAllCommentsByPost(@PathVariable Long postId) {

    return ResponseEntity.ok(
        CommentUtils.listEntityToDTO(commentService.getAllCommentsByPost(postId)));
  }

  @GetMapping("/by-user/{userName}")
  public ResponseEntity<List<CommentDto>> getAllCommentsByUser(@PathVariable String userName) {

    return ResponseEntity.ok(
        CommentUtils.listEntityToDTO(commentService.getAllCommentsByUser(userName)));
  }
}
