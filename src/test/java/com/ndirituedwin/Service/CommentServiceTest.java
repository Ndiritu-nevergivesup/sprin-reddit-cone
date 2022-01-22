package com.ndirituedwin.Service;

import com.ndirituedwin.Exceptions.SpringRedditException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class CommentServiceTest {

    @Test
    @DisplayName("Test should  pass  when comment do not  contain swear  words")
    void containsswearwords() {
        CommentService commentService=new CommentService(null,null,null,null,null,null,null);
//        Assertions.assertFalse(commentService.containsswearwords("This is  a clean text"));
        assertThat(commentService.containsswearwords("This is  a clean text")).isFalse();
    }

    @Test
    @DisplayName(" should throw exception when  text contains swear words")
    public void shouldfailwhencommentcontainsswearwords(){
        CommentService commentService=new CommentService(null,null,null,null,null,null,null);
//        SpringRedditException exception=assertThrows(SpringRedditException.class,() -> {
//            commentService.containsswearwords("This is a shity text");
//        });
//        assertTrue(exception.getMessage().contains("Comment contain unacceptable language"));
        assertThatThrownBy(()->{
            commentService.containsswearwords("This is a shitty comment");
        }).isInstanceOf(SpringRedditException.class).hasMessage("Comment contain unacceptable language");
    }
}