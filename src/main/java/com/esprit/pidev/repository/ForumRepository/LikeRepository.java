package com.esprit.pidev.repository.ForumRepository;

import com.esprit.pidev.entities.Forum.Comment;
import com.esprit.pidev.entities.Forum.LikeEntity;
import com.esprit.pidev.entities.Forum.Post;
import com.esprit.pidev.entities.UserRole.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity,Long> {
    Optional<LikeEntity> findByUserAndPost(User userId, Post post);

    Optional<LikeEntity> findByUserAndComment(User userId, Comment comment);

    Long countByPost(Post post);

    Long countByComment(Comment comment);

    Optional<LikeEntity> findByUserAndPostId(User user, Long postId);


    List<LikeEntity> findAllByPostIsNotNull();

    List<LikeEntity> findAllByCommentIsNotNull();
    LikeEntity findByPostIdAndUserId(Long postId, Long userId);

}
