package com.ndirituedwin.Service;

import com.ndirituedwin.Dto.VoteDto;
import com.ndirituedwin.Exceptions.PostNotFoundException;
import com.ndirituedwin.Exceptions.SpringRedditException;
import com.ndirituedwin.Model.Post;
import com.ndirituedwin.Model.Vote;
import com.ndirituedwin.Repository.PostRepository;
import com.ndirituedwin.Repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.ndirituedwin.Model.VoteType.UPVOTE;

@Service
@Transactional
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public void castvote(VoteDto voteDto) {
    Post post=postRepository.findById(voteDto.getPostId()).orElseThrow(() -> new PostNotFoundException("post with id "+voteDto.getPostId() +" could not be found"));
        Optional<Vote> VoteByPostanduser=voteRepository.findTopByPostAndUserOrderByIdDesc(post,authService.getCurrentUser());
//        System.out.println("votebypostanduser "+VoteByPostanduser.get());
//        System.out.println("votebypostanduser "+VoteByPostanduser);
        if(VoteByPostanduser.isPresent() && VoteByPostanduser.get().getVoteType().equals(voteDto.getVoteType())){
         throw new SpringRedditException("You have already "+voteDto.getVoteType()+" for this post");
     }
         if (UPVOTE.equals(voteDto.getVoteType())){
             post.setVoteCount(post.getVoteCount()+1);
         }else{
             post.setVoteCount(post.getVoteCount()-1);
         }
         voteRepository.save(mapToVote(voteDto,post));
         postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post).user(authService.getCurrentUser())
                .build();
    }
}
