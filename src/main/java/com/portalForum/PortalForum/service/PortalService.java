package com.portalForum.PortalForum.service;

import com.portalForum.PortalForum.dto.PortalDto;
import com.portalForum.PortalForum.exception.NotFoundException;
import com.portalForum.PortalForum.mapper.PortalMapper;
import com.portalForum.PortalForum.model.Portal;
import com.portalForum.PortalForum.repository.PortalRepository;
import com.portalForum.PortalForum.utils.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PortalService {

  private final PortalRepository portalRepository;

  public PortalDto save(PortalDto portalDto) {

    Portal newPortal = portalRepository.save(PortalMapper.INSTANCE.FromDtoToEntity(portalDto));
    portalDto.setId(newPortal.getPortalId());
    portalDto.setNumberOfPosts(0);

    return portalDto;
  }

  @Transactional(readOnly = true)
  public List<Portal> getAll() {

    return portalRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Portal getById(Long id) {

    return portalRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException(ErrorMessage.PORTAL_NOT_FOUND));
  }
}
