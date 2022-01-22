package com.ndirituedwin.Controller;

import com.ndirituedwin.Dto.SubredditDto;
import com.ndirituedwin.Service.SubredditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/api/subreddit")
@AllArgsConstructor
@Slf4j
public class SubredditController {
    
    private final SubredditService subredditService;

    @PostMapping
    public ResponseEntity<?> createSubreddit(@Valid @RequestBody SubredditDto subredditDto, BindingResult result){
                if (result.hasErrors()){
            Map<String,String> errormap=new HashMap<>();
            for (FieldError error: result.getFieldErrors()){
                errormap.put(error.getField(),error.getDefaultMessage());
            }
            return new ResponseEntity<Map<String,String>>(errormap, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(subredditService.save(subredditDto));

    }
    @GetMapping
    public ResponseEntity<List<SubredditDto>> getallsubreddits(){
        return ResponseEntity.ok(subredditService.getall());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubredditDto> getsubredditById(@PathVariable("id") Long id){
        return ResponseEntity.ok(subredditService.getsubredditbyid(id));
    }

}
