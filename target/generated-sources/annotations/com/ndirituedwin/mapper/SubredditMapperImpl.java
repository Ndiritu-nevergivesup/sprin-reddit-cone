package com.ndirituedwin.mapper;

import com.ndirituedwin.Dto.SubredditDto;
import com.ndirituedwin.Dto.SubredditDto.SubredditDtoBuilder;
import com.ndirituedwin.Model.Subreddit;
import com.ndirituedwin.Model.Subreddit.SubredditBuilder;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-21T14:26:36+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 16.0.1 (Oracle Corporation)"
)
@Component
public class SubredditMapperImpl implements SubredditMapper {

    @Override
    public SubredditDto mapSubredditToDto(Subreddit subreddit) {
        if ( subreddit == null ) {
            return null;
        }

        SubredditDtoBuilder subredditDto = SubredditDto.builder();

        if ( subreddit.getId() != null ) {
            subredditDto.id( subreddit.getId() );
        }
        subredditDto.name( subreddit.getName() );
        subredditDto.description( subreddit.getDescription() );

        subredditDto.numberOfPosts( mapPosts(subreddit.getPosts()) );

        return subredditDto.build();
    }

    @Override
    public Subreddit mapDtoToSubreddit(SubredditDto subredditDto) {
        if ( subredditDto == null ) {
            return null;
        }

        SubredditBuilder subreddit = Subreddit.builder();

        subreddit.id( subredditDto.getId() );
        subreddit.name( subredditDto.getName() );
        subreddit.description( subredditDto.getDescription() );

        return subreddit.build();
    }
}
