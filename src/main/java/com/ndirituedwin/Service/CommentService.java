package com.ndirituedwin.Service;

import com.ndirituedwin.Dto.CommentDto;
import com.ndirituedwin.Exceptions.PostNotFoundException;
import com.ndirituedwin.Exceptions.SpringRedditException;
import com.ndirituedwin.Model.Comment;
import com.ndirituedwin.Model.NotificationEmail;
import com.ndirituedwin.Model.Post;
import com.ndirituedwin.Model.User;
import com.ndirituedwin.Repository.CommentRepository;
import com.ndirituedwin.Repository.PostRepository;
import com.ndirituedwin.Repository.UserRepository;
import com.ndirituedwin.mapper.CommentMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {
    private static final String POST_URL="";
    private final PostRepository postRepository;
    private final AuthService authService;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;
    private final MailService mailService;
    private final MailContentBuilder mailContentBuilder;



    public void savecomment(CommentDto commentDto) {
        Post post=postRepository.findById(commentDto.getPostId()).orElseThrow(() -> new PostNotFoundException("Post with id "+commentDto.getPostId()+ " could not be found"));
        Comment comment=commentMapper.map(commentDto,post,authService.getCurrentUser());
           commentRepository.save(comment);
//           return commentDto;
   String message=mailContentBuilder.build(post.getUser().getUsername()+" posted a comment on your post "+POST_URL);
    sendCommentNotification(message,post.getUser());

    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername()+ " Commented on your post",user.getEmail(),message));

    }

    public List<CommentDto> getallcommentsforpost(Long postId) {
        Post post=postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return  commentRepository.findByPost(post).stream().map(commentMapper::mapToDto).collect(Collectors.toList());
    }

    public List<CommentDto> getallcommentsforuser(String username) {
        User user=userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return commentRepository.findAllByUser(user).stream()
                .map(commentMapper::mapToDto).collect(Collectors.toList());
    }

    public boolean containsswearwords(String comment){
        if (comment.contains("shit")){
            throw new SpringRedditException("Comment contain unacceptable language");
        }
        return false;
    }
}
