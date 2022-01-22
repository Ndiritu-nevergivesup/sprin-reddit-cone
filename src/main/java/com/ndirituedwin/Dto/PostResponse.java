package com.ndirituedwin.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Long id;
    private String postname;
    private String url;
    private String description;
    private String username;
    private String subredditname;
    private Integer voteCount;
    private Integer commentCount;
    private String duration;
    private boolean upVote;
    private boolean downVote;
}
