package com.esprit.pidev.entities.Forum;


import com.esprit.pidev.entities.UserRole.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "likeDislike")
public class LikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @JsonIgnoreProperties("likes")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    @JsonIgnoreProperties("likes")
    private Comment comment;

    private boolean liked = true; // new field to store the state of the like

    public LikeEntity(User user, Post post) {
        this.user = user;
        this.post = post;
        this.liked = true;
    }

    public LikeEntity(User user, Comment comment) {
        this.user = user;
        this.comment = comment;
        this.liked = true;
    }


    // constructors, getters, and setters
}
