package com.ndirituedwin.Repository;

import com.ndirituedwin.Dto.PostResponse;
import com.ndirituedwin.Model.Post;
import com.ndirituedwin.Model.Subreddit;
import com.ndirituedwin.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);
}
