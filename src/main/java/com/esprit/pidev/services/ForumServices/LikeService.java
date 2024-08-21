package com.esprit.pidev.services.ForumServices;

import com.esprit.pidev.entities.Forum.Comment;
import com.esprit.pidev.entities.Forum.LikeEntity;
import com.esprit.pidev.entities.Forum.Post;
import com.esprit.pidev.entities.UserRole.User;
import com.esprit.pidev.repository.ForumRepository.CommentRepository;
import com.esprit.pidev.repository.ForumRepository.LikeRepository;
import com.esprit.pidev.repository.ForumRepository.PostRepository;
import com.esprit.pidev.repository.UserRoleRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class LikeService implements ILike {

        @Autowired
        private LikeRepository likeRepository;
        @Autowired
        UserRepository userRepository;
        @Autowired
        private PostRepository postRepository;


        @Autowired
        private CommentRepository commentRepository;
        public User getCurrentUser() {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String username = authentication.getName();
                Optional<User> userOptional = userRepository.findByUsername(username);
                User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found"));
                return user;
        }


        @Override
        public void likePost( Long postId) {
                //User currentUser = getCurrentUser();
                User currentUser = new User();
                currentUser.setId(14L);
                Post post = postRepository.findById(postId)
                        .orElseThrow(() -> new EntityNotFoundException("post not found"));
                LikeEntity like = likeRepository.findByUserAndPost(getCurrentUser(), post).orElseGet(() -> new LikeEntity(currentUser, post));

                if (like.isLiked()) {
                        throw new IllegalArgumentException("Comment already liked by user");
                }

                like.setLiked(true);
                likeRepository.save(like);
        }




        @Override
        public void unlikePost( Long postId) {
                User currentUser = getCurrentUser();
                Post post = postRepository.findById(postId)
                        .orElseThrow(() -> new EntityNotFoundException("Post not found"));
                LikeEntity like = likeRepository.findByUserAndPost(currentUser, post)
                        .orElseThrow(() -> new EntityNotFoundException("Like not found"));

                if (!like.isLiked()) {
                        throw new IllegalArgumentException("Post is not liked by user");
                }

                like.setLiked(false);
                likeRepository.save(like);
        }

        @Override
        public void likeComment(User userId, Long commentId) {
                Comment comment = commentRepository.findById(commentId)
                        .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
                LikeEntity like = likeRepository.findByUserAndComment(userId, comment).orElseGet(() -> new LikeEntity(userId, comment));

                if (like.isLiked()) {
                        throw new IllegalArgumentException("Comment already liked by user");
                }

                like.setLiked(true);
                likeRepository.save(like);
        }

        @Override
        public void unlikeComment(User userId, Long commentId) {
                Comment comment = commentRepository.findById(commentId)
                        .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
                LikeEntity like = likeRepository.findByUserAndComment(userId, comment)
                        .orElseThrow(() -> new EntityNotFoundException("Like not found"));

                if (!like.isLiked()) {
                        throw new IllegalArgumentException("Comment is not liked by user");
                }

                like.setLiked(false);
                likeRepository.save(like);
        }

        public List<LikeEntity> getAllPostLikes() {
                return likeRepository.findAllByPostIsNotNull();
        }

        public List<LikeEntity> getAllCommentLikes() {
                return likeRepository.findAllByCommentIsNotNull();
        }


        public boolean isPostLikedByUser( Long postId) {
                //User currentUser = getCurrentUser();
                User currentUser = new User();
                currentUser.setId(14L);

                Post post = postRepository.findById(postId)
                        .orElseThrow(() -> new EntityNotFoundException("Post not found"));
                Optional<LikeEntity> like = likeRepository.findByUserAndPost(currentUser, post);
                return like.map(LikeEntity::isLiked).orElse(false);
        }

        public boolean isCommentLikedByUser(User user, Long commentId) {
                Comment comment = commentRepository.findById(commentId)
                        .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
                Optional<LikeEntity> like = likeRepository.findByUserAndComment(user, comment);
                return like.map(LikeEntity::isLiked).orElse(false);
        }


}