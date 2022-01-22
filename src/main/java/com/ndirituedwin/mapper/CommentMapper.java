package com.ndirituedwin.mapper;

import com.ndirituedwin.Dto.CommentDto;
import com.ndirituedwin.Model.Comment;
import com.ndirituedwin.Model.Post;
import com.ndirituedwin.Model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "id", ignore = true/*source = "commentDto.id"*/)
    @Mapping(target = "post",source = "post")
    @Mapping(target = "user",source = "user")
    @Mapping(target = "text",source = "commentDto.text")
    @Mapping(target = "createdDate",expression = "java(java.time.Instant.now())")
    Comment map(CommentDto commentDto, Post post, User user);

    @Mapping(target = "postId",expression = "java(comment.getPost().getId())")
    @Mapping(target = "username",expression = "java(comment.getUser().getUsername())")
    CommentDto mapToDto(Comment comment);
}
