package com.ndirituedwin.Controller;

import com.ndirituedwin.Dto.VoteDto;
import com.ndirituedwin.Service.VoteService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/votes")
@AllArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<?> casvote(@Valid @RequestBody VoteDto voteDto, BindingResult result){

        voteService.castvote(voteDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
