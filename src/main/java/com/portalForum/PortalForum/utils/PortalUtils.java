package com.portalForum.PortalForum.utils;

import com.portalForum.PortalForum.dto.PortalDto;
import com.portalForum.PortalForum.mapper.PortalMapper;
import com.portalForum.PortalForum.model.Portal;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PortalUtils {

  public static List<PortalDto> listEntityToDTO(List<Portal> portals) {

    return portals.stream()
        .map(PortalMapper.INSTANCE::fromEntityToDto)
        .collect(Collectors.toList());
  }
}
