package com.portalForum.PortalForum.utils;

import com.portalForum.PortalForum.dto.PostResponse;
import com.portalForum.PortalForum.mapper.PostMapper;
import com.portalForum.PortalForum.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostUtils {

  private final PostMapper postMapper;

  public List<PostResponse> listEntityToDTO(List<Post> posts) {

    return posts.stream().map(postMapper::fromEntityToDto).collect(Collectors.toList());
  }
}
