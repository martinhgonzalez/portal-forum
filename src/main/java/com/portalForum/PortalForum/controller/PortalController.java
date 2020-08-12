package com.portalForum.PortalForum.controller;

import com.portalForum.PortalForum.dto.PortalDto;
import com.portalForum.PortalForum.mapper.PortalMapper;
import com.portalForum.PortalForum.service.PortalService;
import com.portalForum.PortalForum.utils.PortalUtils;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/portals")
public class PortalController {

  private final PortalService portalService;

  @PostMapping
  public ResponseEntity<PortalDto> createPortal(@Valid @RequestBody PortalDto portalDto) {

    return ResponseEntity.status(HttpStatus.CREATED).body(portalService.save(portalDto));
  }

  @GetMapping
  public ResponseEntity<List<PortalDto>> getAllPortals() {

    return ResponseEntity.ok(PortalUtils.listEntityToDTO(portalService.getAll()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<PortalDto> getPortalById(@PathVariable Long id) {

    return ResponseEntity.ok(PortalMapper.INSTANCE.fromEntityToDto(portalService.getById(id)));
  }
}
