package com.ndirituedwin.Service;

import com.ndirituedwin.Dto.PostRequest;
import com.ndirituedwin.Dto.PostResponse;
import com.ndirituedwin.Exceptions.PostNotFoundException;
import com.ndirituedwin.Exceptions.SubredditNotFoundException;
import com.ndirituedwin.Model.Post;
import com.ndirituedwin.Model.Subreddit;
import com.ndirituedwin.Model.User;
import com.ndirituedwin.Repository.PostRepository;
import com.ndirituedwin.Repository.SubredditRepository;
import com.ndirituedwin.Repository.UserRepository;
import com.ndirituedwin.mapper.PostMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class PostService {

    private final AuthService authService;
    private final SubredditRepository subredditRepository;
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserRepository userRepository;

    public Post save(PostRequest postRequest){
        Subreddit subreddit=subredditRepository.findByName(postRequest.getSubredditName()).orElseThrow(() ->
                new SubredditNotFoundException("subreddit not found "+postRequest.getSubredditName()));
        System.out.println("subreddit_subreddit "+subreddit);
        User currentUser=authService.getCurrentUser();
        System.out.println("currentuser "+currentUser);
        return postRepository.save(postMapper.map(postRequest,subreddit,currentUser));
//        Post post= postRepository.save(postMapper.map(postRequest,subreddit,currentUser));
//        postRequest.setId(post.getId());
//        return post;


//        Post post=postRepository.save(postMapper.map(postRequest,subreddit,currentUser));
//        postRequest.setId(post.getId());
//        postRequest.setPostName(post.getPostName());
//        postRequest.setSubredditName(post.getSubreddit().getName());
//        postRequest.setDescription(post.getDescription());
//        postRequest.setUrl(post.getUrl());
//        System.out.println("printing_the_saved_post "+ postRequest);
//        return postRequest;
//        return postMapper.map(postRequest,subreddit,currentUser);
    }

    @Transactional(readOnly = true)
    public PostResponse getpostbyid(Long id) {
         Post post=postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("post with id "+id+" could not be found"));
        log.info("subreddit",post);
        System.out.println("subreddit "+post);
         return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getallposts() {
        return postRepository.findAll().stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubreddit(Long subredditid) {
        Subreddit subreddit=subredditRepository.findById(subredditid)
                .orElseThrow(() -> new SubredditNotFoundException("subreddit with id "+subredditid+" could not be found"));
        log.info("subreddit",subreddit);
        System.out.println("subreddit "+subreddit);
        List<Post> posts=postRepository.findAllBySubreddit(subreddit);
        log.info("posts",posts);
        System.out.println("posts "+posts);
        return posts.stream().map(postMapper::mapToDto).collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByuser(String username) {
      User user=userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user with username "+username+ " could not be found"));
        log.info("user",user);
        System.out.println("user "+user);
       return postRepository.findByUser(user).stream().map(postMapper::mapToDto).collect(Collectors.toList());

    }
}
