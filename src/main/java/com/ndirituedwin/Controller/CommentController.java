package com.ndirituedwin.Controller;

import com.ndirituedwin.Dto.CommentDto;
import com.ndirituedwin.Service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {


    private final CommentService commentService;

    @PostMapping("/savecomment")
    public ResponseEntity<?> savecomment(@Valid @RequestBody CommentDto commentDto, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errormap = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errormap.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<Map<String, String>>(errormap, HttpStatus.BAD_REQUEST);
        }
        commentService.savecomment(commentDto);
        return new ResponseEntity(HttpStatus.CREATED);
//        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.savecomment(commentDto));
    }

    @GetMapping("/commentsforpost/{postId}")
    public ResponseEntity<List<CommentDto>> commentsforpost(@PathVariable("postId") Long postId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getallcommentsforpost(postId));
    }

    @GetMapping("/commentsforuser/{username}")
    public ResponseEntity<List<CommentDto>> commentsforauser(@PathVariable("username") String username){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getallcommentsforuser(username));
    }
}
