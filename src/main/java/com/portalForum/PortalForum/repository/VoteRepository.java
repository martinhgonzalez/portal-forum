package com.portalForum.PortalForum.repository;

import com.portalForum.PortalForum.model.Post;
import com.portalForum.PortalForum.model.User;
import com.portalForum.PortalForum.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {

  Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);

  @Query(
      value =
          "SELECT((select count(vote_id) from votes where vote_type = 'UPVOTE' and post_id = :postId) - (select count(vote_id) from votes where vote_type = 'DOWNVOTE' and post_id = :postId )) as vote_count",
      nativeQuery = true)
  Integer countVotesByPostId(@Param("postId") Long postId);
}
