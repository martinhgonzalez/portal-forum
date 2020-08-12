package com.portalForum.PortalForum.service;

import com.portalForum.PortalForum.dto.PostRequest;
import com.portalForum.PortalForum.exception.NotFoundException;
import com.portalForum.PortalForum.mapper.PostMapper;
import com.portalForum.PortalForum.model.Portal;
import com.portalForum.PortalForum.model.Post;
import com.portalForum.PortalForum.model.User;
import com.portalForum.PortalForum.repository.PostRepository;
import com.portalForum.PortalForum.utils.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

  private final PostRepository postRepository;
  private final PortalService portalService;
  private final AuthService authService;
  private final PostMapper postMapper;

  public void save(PostRequest postRequest) {
    Portal portal = portalService.getById(postRequest.getPortalId());
    postRepository.save(
        postMapper.fromDtoToEntity(postRequest, portal, authService.getCurrentUser()));
  }

  @Transactional(readOnly = true)
  public List<Post> getAllPosts() {
    return postRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Post getPostById(Long id) {

    return postRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException(ErrorMessage.POST_NOT_FOUND));
  }

  @Transactional(readOnly = true)
  public List<Post> getPostsByPortal(Long portalId) {
    Portal portal = portalService.getById(portalId);

    return postRepository.findAllByPortal(portal);
  }

  @Transactional(readOnly = true)
  public List<Post> getPostsByUsername(String username) {
    User user = authService.findUserByUsername(username);

    return postRepository.findByUser(user);
  }
}
