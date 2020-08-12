package com.portalForum.PortalForum.service;

import com.portalForum.PortalForum.dto.VoteDto;
import com.portalForum.PortalForum.mapper.VoteMapper;
import com.portalForum.PortalForum.model.Post;
import com.portalForum.PortalForum.model.User;
import com.portalForum.PortalForum.model.Vote;
import com.portalForum.PortalForum.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class VoteService {

  private final VoteRepository voteRepository;
  private final PostService postService;
  private final AuthService authService;

  @Transactional
  public void vote(VoteDto voteDto) {
    Post post = postService.getPostById(voteDto.getPostId());

    User currentUser = authService.getCurrentUser();

    Optional<Vote> voteByPostAndUser = findByUserAndPost(post, currentUser);
    if (!voteByPostAndUser.isPresent()) {
      voteRepository.save(VoteMapper.INSTANCE.FromDtoToEntity(voteDto, post, currentUser));
    } else if (voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
      voteRepository.delete(voteByPostAndUser.get());
    } else {
      Vote newVote = VoteMapper.INSTANCE.FromDtoToEntity(voteDto, post, currentUser);
      newVote.setVoteId(voteByPostAndUser.get().getVoteId());
      voteRepository.save(newVote);
    }
  }

  public Integer countVotesByPostId(Long postId) {
    return voteRepository.countVotesByPostId(postId);
  }

  public Optional<Vote> findByUserAndPost(Post post, User user) {

    return voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, user);
  }
}
