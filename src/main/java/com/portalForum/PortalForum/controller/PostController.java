package com.portalForum.PortalForum.controller;

import com.portalForum.PortalForum.dto.PostRequest;
import com.portalForum.PortalForum.dto.PostResponse;
import com.portalForum.PortalForum.mapper.PostMapper;
import com.portalForum.PortalForum.service.PostService;
import com.portalForum.PortalForum.utils.PostUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/posts/")
@RequiredArgsConstructor
public class PostController {

  private final PostService postService;
  private final PostMapper postMapper;
  private final PostUtils postUtils;

  @PostMapping
  public ResponseEntity<Void> createPost(@Valid @RequestBody PostRequest postRequest) {
    postService.save(postRequest);

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<PostResponse>> getAllPosts() {

    return status(HttpStatus.OK).body(postUtils.listEntityToDTO(postService.getAllPosts()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {

    return status(HttpStatus.OK).body(postMapper.fromEntityToDto(postService.getPostById(id)));
  }

  @GetMapping("by-portal/{id}")
  public ResponseEntity<List<PostResponse>> getPostsByPortal(@PathVariable Long id) {

    return status(HttpStatus.OK).body(postUtils.listEntityToDTO(postService.getPostsByPortal(id)));
  }

  @GetMapping("by-username/{username}")
  public ResponseEntity<List<PostResponse>> getPostsByUsername(@PathVariable String username) {

    return status(HttpStatus.OK)
        .body(postUtils.listEntityToDTO(postService.getPostsByUsername(username)));
  }
}
