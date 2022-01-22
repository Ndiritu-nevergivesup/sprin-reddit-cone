package com.ndirituedwin.Repository;

import com.ndirituedwin.Dto.CommentDto;
import com.ndirituedwin.Model.Comment;
import com.ndirituedwin.Model.Post;
import com.ndirituedwin.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByPost(Post post);
    List<Comment> findAllByUser(User user);
}
