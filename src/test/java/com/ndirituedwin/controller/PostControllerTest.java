package com.ndirituedwin.controller;

import com.ndirituedwin.Controller.PostController;
import com.ndirituedwin.Dto.PostResponse;
import com.ndirituedwin.Model.VoteType;
import com.ndirituedwin.Service.PostService;
import com.ndirituedwin.Service.UserDetailsServiceimpl;
import com.ndirituedwin.security.JwtProvider;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static java.util.Arrays.asList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PostController.class)
public class PostControllerTest {

    @MockBean
    private PostService postService;
    @MockBean
    private UserDetailsServiceimpl userDetailsServiceimpl;

    @MockBean
    private JwtProvider jwtProvider;


    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should list all posts when making get to /api/posts")
    public void shouldreadallposts() throws Exception {

        PostResponse postResponse1=new PostResponse(1L,"Post Name","www.url.com","description i","ddd","subreddin one",12,13, "1 day ago",false,false);
        PostResponse postResponse2=new PostResponse(2L,"POST TWO","www.ff.com","descr","username","subreddit",121,787,"1 day ago",false,false);
        Mockito.when(postService.getallposts()).thenReturn(Arrays.asList(postResponse1,postResponse2));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/getallposts")).andExpect(MockMvcResultMatchers.status()
                .is(200))

                   .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(2)))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].id",Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].postname",Matchers.is("Post Name")))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].url",Matchers.is("www.url.com")))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].url",Matchers.is("www.ff.com")))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].id",Matchers.is(2)));
    }
}
