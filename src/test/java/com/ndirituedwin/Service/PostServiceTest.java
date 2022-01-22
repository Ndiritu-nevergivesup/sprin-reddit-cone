package com.ndirituedwin.Service;

import com.ndirituedwin.Dto.PostRequest;
import com.ndirituedwin.Dto.PostResponse;
import com.ndirituedwin.Model.Post;
import com.ndirituedwin.Model.Subreddit;
import com.ndirituedwin.Model.User;
import com.ndirituedwin.Repository.PostRepository;
import com.ndirituedwin.Repository.SubredditRepository;
import com.ndirituedwin.Repository.UserRepository;
import com.ndirituedwin.mapper.PostMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Collections;
import java.util.Optional;



@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private  AuthService authService;
    @Mock
    private  SubredditRepository subredditRepository;
    @Mock
    private  PostRepository postRepository;
    @Mock
    private  PostMapper postMapper;
    @Mock
    private  UserRepository userRepository;
    @Captor
    private ArgumentCaptor<Post> postArgumentCaptor;

   PostService postService;

    @BeforeEach
    public void setup(){
        postService=new PostService(authService,subredditRepository,postRepository,postMapper,userRepository);

    }
    @Test
    @DisplayName("Should find post by id")
    void shouldFindPostById() {
//        PostService postService=new PostService(authService,subredditRepository,postRepository,postMapper,userRepository);
        Post post=new Post(123L,"First Post","www.google.com","post one description",0,null, Instant.now(),null);

        PostResponse expectedResponse=new PostResponse(123L,"First Post","www.google.com","post one description","test user","subreddit test",20,25,"1 hour ago",false,false);
        Mockito.when(postRepository.findById(123L)).thenReturn(Optional.of(post));
        Mockito.when(postMapper.mapToDto(Mockito.any(Post.class))).thenReturn(expectedResponse);

        PostResponse actualresponse=postService.getpostbyid(123L);
        Assertions.assertThat(actualresponse.getId()).isEqualTo(expectedResponse.getId());
        Assertions.assertThat(actualresponse.getPostname()).isEqualTo(expectedResponse.getPostname());
    }

    @Test
    @DisplayName("should save post")
public void ShouldSavePosts(){

//        PostService postService=new PostService(authService,subredditRepository,postRepository,postMapper,userRepository);
        User currentUser=new User(123L,"test user","password","user@email.com",Instant.now(),true);
        Subreddit subreddit=new Subreddit(123L,"subreddit one","description one", Collections.emptyList(),Instant.now(),currentUser);
        Post post=new Post(123L,"post one","www.google.com","description",0,currentUser,Instant.now(),null);
        PostRequest postRequest=new PostRequest(123L,"subreddit one","first post","www.google.com","description");

        Mockito.when(subredditRepository.findByName("subreddit one")).thenReturn(Optional.of(subreddit));
        Mockito.when(postMapper.map(postRequest,subreddit,currentUser)).thenReturn(post);
        Mockito.when(authService.getCurrentUser()).thenReturn(currentUser);
        postService.save(postRequest);
        Mockito.verify(postRepository,Mockito.times(1)).save(postArgumentCaptor.capture());

        Assertions.assertThat(postArgumentCaptor.getValue().getId()).isEqualTo(123L);
        Assertions.assertThat(postArgumentCaptor.getValue().getPostName()).isEqualTo("post one");
    }


}