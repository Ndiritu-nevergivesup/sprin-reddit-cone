package com.ndirituedwin.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "comment required")
    @NotEmpty
    private String text;
    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name ="postId", referencedColumnName = "postId")
    private Post post;
    private Instant createdDate;
    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "userId",referencedColumnName = "userId")
    private User user;

}
