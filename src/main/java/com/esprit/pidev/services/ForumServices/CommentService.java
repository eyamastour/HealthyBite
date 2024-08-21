package com.esprit.pidev.services.ForumServices;

import com.esprit.pidev.entities.Forum.Comment;
import com.esprit.pidev.entities.Forum.LikeEntity;
import com.esprit.pidev.entities.Forum.Post;
import com.esprit.pidev.entities.UserRole.User;
import com.esprit.pidev.repository.ForumRepository.CommentRepository;
import com.esprit.pidev.repository.ForumRepository.LikeRepository;
import com.esprit.pidev.repository.ForumRepository.PostRepository;
import com.esprit.pidev.repository.UserRoleRepository.UserRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CommentService implements IComment {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
     IPost postService;
    @Autowired
    LikeRepository likeRepository;
    @Autowired
    PostRepository postRepository;



    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment addReply(Long commentId, Comment reply) {
        reply.setUser(getCurrentUser());
        Comment parentComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + commentId));

        reply.setParentComment(parentComment);

        return commentRepository.save(reply);
    }


    @Override
    public Comment addComment(Comment comment, Long postId) {

            comment.setUser(getCurrentUser());
            Post post = postService.retrievePostById(postId);
            if (post == null) {
                throw new IllegalArgumentException("Post not found");
            }
            if (comment.getContent() == null || comment.getContent().isEmpty()) {
                throw new IllegalArgumentException("Comment content is empty");
            }
            comment.setPost(post);

        return commentRepository.save(comment);
    }



    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }






    /*@Override
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
*/


    public List<Comment> getCommentsByUserId(Long userId) {
        return commentRepository.findByUserId(userId);
    }







    @Override
    public Comment updateComment(Long id, Comment comment) {
        comment.setUser(getCurrentUser());
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Comment with id " + id + " not found"));

        existingComment.setContent(comment.getContent());
        existingComment.setUser(comment.getUser());

        return commentRepository.save(existingComment);
    }


    @Override
    public Comment retrieveCommentById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Comment> retrieveAllComment() {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        if (!response.isCommitted()) {
            return commentRepository.findAll();
        } else {
            throw new RuntimeException("Could not get response");
        }
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }


    @Override
    public List<Comment> retrieveCommentsByPost(Post post) {
        return commentRepository.findByPost(post);

    }
}
