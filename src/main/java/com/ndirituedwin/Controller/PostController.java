package com.ndirituedwin.Controller;

import com.ndirituedwin.Dto.PostRequest;
import com.ndirituedwin.Dto.PostResponse;
import com.ndirituedwin.Service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
//@CrossOrigin("*")
public class PostController {
    private final PostService postService;
    @PostMapping("/savepost")
    public ResponseEntity<PostRequest> createPost(@Valid @RequestBody PostRequest postRequest, BindingResult result){
         postService.save(postRequest);
         return new ResponseEntity(HttpStatus.CREATED);
//        return ResponseEntity.status(HttpStatus.CREATED).body(postService.save(postRequest));

    }
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getpostbyid(@PathVariable("id") Long id){
        return ResponseEntity.ok(postService.getpostbyid(id));
    }
    @GetMapping("/getallposts")
    public ResponseEntity<List<PostResponse>> getallposts(){
        return ResponseEntity.ok(postService.getallposts());
    }
    @GetMapping("/by_subreddit/{subredditid}")
    public ResponseEntity<List<PostResponse>> getpostsbysubreddit(@PathVariable("subredditid") Long subredditid){
        return ResponseEntity.ok(postService.getPostsBySubreddit(subredditid));
    }
    @GetMapping("/by_user/{username}")
    public ResponseEntity<List<PostResponse>> getpostsbyuser(@PathVariable("username") String username){
        return ResponseEntity.ok(postService.getPostsByuser(username));
    }

}
