package com.portalForum.PortalForum.controller;

import com.portalForum.PortalForum.dto.VoteDto;
import com.portalForum.PortalForum.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/votes/")
public class VoteController {

  private final VoteService voteService;

  @PostMapping
  public ResponseEntity<Void> vote(@Valid @RequestBody VoteDto voteDto) {
    voteService.vote(voteDto);

    return new ResponseEntity<>(HttpStatus.OK);
  }
}
