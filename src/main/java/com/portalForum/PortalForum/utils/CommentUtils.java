package com.portalForum.PortalForum.utils;

import com.portalForum.PortalForum.dto.CommentDto;
import com.portalForum.PortalForum.mapper.CommentMapper;
import com.portalForum.PortalForum.model.Comment;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CommentUtils {

  public static List<CommentDto> listEntityToDTO(List<Comment> comment) {

    return comment.stream()
        .map(CommentMapper.INSTANCE::fromEntityToDto)
        .collect(Collectors.toList());
  }
}
