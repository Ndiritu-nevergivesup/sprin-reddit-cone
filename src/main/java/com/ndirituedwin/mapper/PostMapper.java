package com.ndirituedwin.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.ndirituedwin.Dto.PostRequest;
import com.ndirituedwin.Dto.PostResponse;
import com.ndirituedwin.Model.*;
import com.ndirituedwin.Repository.CommentRepository;
import com.ndirituedwin.Repository.VoteRepository;
import com.ndirituedwin.Service.AuthService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.ndirituedwin.Model.VoteType.DOWNVOTE;
import static com.ndirituedwin.Model.VoteType.UPVOTE;

@Mapper(componentModel = "spring")
public abstract  class   PostMapper {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AuthService authService;



    @Mapping(target = "id",source = "postRequest.id")
    @Mapping(target = "createdDate",expression = "java(java.time.Instant.now())")
    @Mapping(target = "subreddit",source = "subreddit")
    @Mapping(target = "user",source = "user")
    @Mapping(target = "description",source = "postRequest.description")
    @Mapping(target = "voteCount", constant = "0")
    public abstract   Post map(PostRequest postRequest, Subreddit subreddit, User user);

    @Mapping(target = "id",source = "post.id")
    @Mapping(target = "postname",source = "postName")
    @Mapping(target = "description",source = "description")
    @Mapping(target = "url",source = "url")
    @Mapping(target = "subredditname",source = "subreddit.name")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    @Mapping(target = "username",source = "user.username")
    @Mapping(target = "upVote",expression = "java(isPostUpVoted(post))")
    @Mapping(target = "downVote",expression = "java(isPostDownVoted(post))")
    public abstract PostResponse mapToDto(Post post);
    Integer commentCount(Post post){
        return commentRepository.findByPost(post).size();
    }
    String getDuration(Post post){
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }
    boolean isPostUpVoted(Post post){
        return checkVoteType(post,UPVOTE);
    }
    boolean isPostDownVoted(Post post){
        return checkVoteType(post,DOWNVOTE);
    }
    private boolean checkVoteType(Post post, VoteType voteType){
        if (authService.isLoggedIn()){
            Optional<Vote> voteforPostByUser=voteRepository.findTopByPostAndUserOrderByIdDesc(post,authService.getCurrentUser());
            return voteforPostByUser.filter(vote -> vote.getVoteType().equals(voteType)).isPresent();
        }
        return  false;
    }




}
