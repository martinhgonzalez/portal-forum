package com.portalForum.PortalForum.mapper;

import com.portalForum.PortalForum.dto.CommentDto;
import com.portalForum.PortalForum.model.Comment;
import com.portalForum.PortalForum.model.Post;
import com.portalForum.PortalForum.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {

  CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

  @Mapping(target = "commentId", ignore = true)
  @Mapping(target = "text", source = "commentDto.text")
  @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
  @Mapping(target = "post", source = "post")
  @Mapping(target = "user", source = "user")
  Comment fromDtoToEntity(CommentDto commentDto, Post post, User user);

  @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
  @Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")
  @Mapping(target = "id", source = "commentId")
  CommentDto fromEntityToDto(Comment comment);
}
