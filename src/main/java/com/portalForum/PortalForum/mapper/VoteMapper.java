package com.portalForum.PortalForum.mapper;

import com.portalForum.PortalForum.dto.VoteDto;
import com.portalForum.PortalForum.model.Post;
import com.portalForum.PortalForum.model.User;
import com.portalForum.PortalForum.model.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VoteMapper {

  VoteMapper INSTANCE = Mappers.getMapper(VoteMapper.class);

  @Mapping(target = "voteType", source = "voteDto.voteType")
  @Mapping(target = "post", source = "post")
  @Mapping(target = "user", source = "user")
  Vote FromDtoToEntity(VoteDto voteDto, Post post, User user);
}
