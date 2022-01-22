package com.ndirituedwin.mapper;

import com.ndirituedwin.Dto.PostRequest;
import com.ndirituedwin.Dto.PostResponse;
import com.ndirituedwin.Model.Post;
import com.ndirituedwin.Model.Post.PostBuilder;
import com.ndirituedwin.Model.Subreddit;
import com.ndirituedwin.Model.User;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-21T14:26:35+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 16.0.1 (Oracle Corporation)"
)
@Component
public class PostMapperImpl extends PostMapper {

    @Override
    public Post map(PostRequest postRequest, Subreddit subreddit, User user) {
        if ( postRequest == null && subreddit == null && user == null ) {
            return null;
        }

        PostBuilder post = Post.builder();

        if ( postRequest != null ) {
            post.id( postRequest.getId() );
            post.description( postRequest.getDescription() );
            post.postName( postRequest.getPostName() );
            post.url( postRequest.getUrl() );
        }
        if ( subreddit != null ) {
            post.subreddit( subreddit );
        }
        if ( user != null ) {
            post.user( user );
        }
        post.createdDate( java.time.Instant.now() );
        post.voteCount( 0 );

        return post.build();
    }

    @Override
    public PostResponse mapToDto(Post post) {
        if ( post == null ) {
            return null;
        }

        PostResponse postResponse = new PostResponse();

        postResponse.setId( post.getId() );
        postResponse.setPostname( post.getPostName() );
        postResponse.setDescription( post.getDescription() );
        postResponse.setUrl( post.getUrl() );
        postResponse.setSubredditname( postSubredditName( post ) );
        postResponse.setUsername( postUserUsername( post ) );
        postResponse.setVoteCount( post.getVoteCount() );

        postResponse.setCommentCount( commentCount(post) );
        postResponse.setDuration( getDuration(post) );
        postResponse.setUpVote( isPostUpVoted(post) );
        postResponse.setDownVote( isPostDownVoted(post) );

        return postResponse;
    }

    private String postSubredditName(Post post) {
        if ( post == null ) {
            return null;
        }
        Subreddit subreddit = post.getSubreddit();
        if ( subreddit == null ) {
            return null;
        }
        String name = subreddit.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String postUserUsername(Post post) {
        if ( post == null ) {
            return null;
        }
        User user = post.getUser();
        if ( user == null ) {
            return null;
        }
        String username = user.getUsername();
        if ( username == null ) {
            return null;
        }
        return username;
    }
}
