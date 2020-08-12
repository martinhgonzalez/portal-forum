package com.portalForum.PortalForum.mapper;

import com.portalForum.PortalForum.dto.PortalDto;
import com.portalForum.PortalForum.model.Portal;
import com.portalForum.PortalForum.model.Post;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PortalMapper {

  PortalMapper INSTANCE = Mappers.getMapper(PortalMapper.class);

  @Mapping(target = "numberOfPosts", expression = "java(mapPosts(portal.getPosts()))")
  @Mapping(source = "portalId", target = "id")
  PortalDto fromEntityToDto(Portal portal);

  default Integer mapPosts(List<Post> numberOfPosts) {

    return numberOfPosts.size();
  }

  @InheritInverseConfiguration
  @Mapping(source = "id", target = "portalId")
  @Mapping(target = "posts", ignore = true)
  @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
  Portal FromDtoToEntity(PortalDto portalDto);
}
