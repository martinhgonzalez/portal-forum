package com.portalForum.PortalForum.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.portalForum.PortalForum.dto.PostRequest;
import com.portalForum.PortalForum.dto.PostResponse;
import com.portalForum.PortalForum.model.Portal;
import com.portalForum.PortalForum.model.Post;
import com.portalForum.PortalForum.model.User;
import com.portalForum.PortalForum.model.Vote;
import com.portalForum.PortalForum.model.VoteType;
import com.portalForum.PortalForum.service.AuthService;
import com.portalForum.PortalForum.service.CommentService;
import com.portalForum.PortalForum.service.VoteService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.Optional;

import static com.portalForum.PortalForum.model.VoteType.DOWNVOTE;
import static com.portalForum.PortalForum.model.VoteType.UPVOTE;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

  private CommentService commentService;
  private VoteService voteService;
  private AuthService authService;

  @Autowired
  public void setCommentService(@Lazy CommentService commentService) {
    this.commentService = commentService;
  }

  @Autowired
  public void setVoteService(@Lazy VoteService voteService) {
    this.voteService = voteService;
  }

  @Autowired
  public void setAuthService(@Lazy AuthService authService) {
    this.authService = authService;
  }

  @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
  @Mapping(target = "description", source = "postRequest.description")
  @Mapping(target = "portal", source = "portal")
  @Mapping(target = "user", source = "user")
  public abstract Post fromDtoToEntity(PostRequest postRequest, Portal portal, User user);

  @Mapping(target = "id", source = "postId")
  @Mapping(target = "portalName", source = "portal.name")
  @Mapping(target = "userName", source = "user.username")
  @Mapping(target = "commentCount", expression = "java(commentCount(post))")
  @Mapping(target = "duration", expression = "java(getDuration(post))")
  @Mapping(target = "upVote", expression = "java(isPostUpVoted(post))")
  @Mapping(target = "downVote", expression = "java(isPostDownVoted(post))")
  @Mapping(target = "voteCount", expression = "java(voteCount(post))")
  public abstract PostResponse fromEntityToDto(Post post);

  Integer commentCount(Post post) {
    return commentService.getAllCommentsByPost(post.getPostId()).size();
  }

  Integer voteCount(Post post) {
    return voteService.countVotesByPostId(post.getPostId());
  }

  String getDuration(Post post) {
    return TimeAgo.using(post.getCreatedDate().toEpochMilli());
  }

  boolean isPostUpVoted(Post post) {
    return checkVoteType(post, UPVOTE);
  }

  boolean isPostDownVoted(Post post) {
    return checkVoteType(post, DOWNVOTE);
  }

  private boolean checkVoteType(Post post, VoteType voteType) {
    if (authService.isLoggedIn()) {
      Optional<Vote> voteForPostByUser =
          voteService.findByUserAndPost(post, authService.getCurrentUser());

      return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType)).isPresent();
    }

    return false;
  }
}
