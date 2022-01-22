package com.ndirituedwin.Service;

import com.ndirituedwin.Dto.SubredditDto;
import com.ndirituedwin.Exceptions.SpringRedditException;
import com.ndirituedwin.Model.Subreddit;
import com.ndirituedwin.Repository.SubredditRepository;
import com.ndirituedwin.mapper.SubredditMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class SubredditService {

 private final SubredditRepository subredditRepository;
 private final SubredditMapper subredditMapper;


 @Transactional
    public SubredditDto save(SubredditDto subredditDto){
//     Subreddit save=subredditRepository.save(mapSubredditDto(subredditDto));
     Subreddit save=subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
       subredditDto.setId(save.getId());
       log.info("subreddits->",save);
       return subredditDto;

    }

    private Subreddit mapSubredditDto(SubredditDto subredditDto) {
        return Subreddit.builder().name(subredditDto.getName()).description(subredditDto.getDescription()).build();
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getall() {
    return subredditRepository
            .findAll()
            .stream()
//            .map(this::mapToDto)
            .map(subredditMapper::mapSubredditToDto)
            .collect(Collectors.toList());
    }

    public SubredditDto getsubredditbyid(Long id) {
    Subreddit subreddit=subredditRepository.findById(id).orElseThrow(() -> new SpringRedditException("subreddit with id "+id+ " not found"));
     return subredditMapper.mapSubredditToDto(subreddit);

 }

//    private SubredditDto maptoDto(Subreddit subreddit) {
//     return SubredditDto.builder().name(subreddit.getName()).id(subreddit.getId()).numberOfPosts(subreddit.getPosts().size()).build();
// }

}
