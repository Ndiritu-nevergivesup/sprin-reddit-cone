package com.ndirituedwin.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {

 private long id;
 private String subredditName;
 private String postName;
 private String url;
 private String description;

}
