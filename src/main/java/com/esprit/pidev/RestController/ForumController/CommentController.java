package com.esprit.pidev.RestController.ForumController;

import com.esprit.pidev.entities.Forum.Comment;
import com.esprit.pidev.entities.Forum.Post;
import com.esprit.pidev.entities.UserRole.User;
import com.esprit.pidev.repository.ForumRepository.CommentRepository;
import com.esprit.pidev.repository.ForumRepository.PostRepository;
import com.esprit.pidev.repository.UserRoleRepository.UserRepository;
import com.esprit.pidev.services.ForumServices.CommentService;
import com.esprit.pidev.services.ForumServices.IComment;
import com.esprit.pidev.services.ForumServices.LikeService;
import com.esprit.pidev.services.ForumServices.PostService;
import lombok.AllArgsConstructor;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;

@RequestMapping("/api/test")
@RestController
@AllArgsConstructor
public class CommentController {

     IComment iComment;
@Autowired
LikeService likeService;
    @Autowired
     CommentService commentService;
    @Autowired
     PostService postService;


     CommentRepository commentRepository;
     PostRepository postRepository;
     UserRepository userRepository;











    @PostMapping("/{postId}/comments")
    public Comment addComment( @PathVariable Long postId, @RequestBody Comment comment) {
        Post post = postService.retrievePostById(postId);
        if (post == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
        comment.setPost(post);
        return commentService.addComment(comment, postId);
    }



    @PostMapping("/{commentId}/replies")
    public Comment addReply(@PathVariable Long commentId, @RequestBody Comment reply) {

        return commentService.addReply(commentId,reply);
    }


    @GetMapping("/getPostComments")
    public ResponseEntity<List<Comment>> getPostComments(@RequestParam Long postId) {
        Post post = postService.retrievePostById(postId);
        List<Comment> comments = commentService.retrieveCommentsByPost(post);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/post/{postId}/comments")
    public List<Comment> getCommentsByPostId(@PathVariable Long postId) {
        Post post = postService.retrievePostById(postId);
        if(post == null){
            throw new IllegalArgumentException("Post not found");
        }
        return post.getComments();
    }

    @PutMapping("/updateComment/{id}")
    public Comment updateComment(@PathVariable("id") Long id, @RequestBody Comment comment) {
        return iComment.updateComment(id, comment);
    }



    @GetMapping("/getCommentById/{id}")
    public Comment retrieveCommentById(@PathVariable("id") Long id){
        return iComment.retrieveCommentById(id);
    }


    @GetMapping("/getAllComment")
    public List<Comment> retrieveAllComment(){
        return iComment.retrieveAllComment();
    }


    @GetMapping("/getCommentsByPost/{postId}")
    public List<Comment> retrieveCommentsByPost(@PathVariable("postId") Long postId){
        Post post = postService.retrievePostById(postId);
        return commentService.retrieveCommentsByPost(post);
    }

    @DeleteMapping("/deleteComment/{id}")
    public void deleteComment(@PathVariable("id") Long id){
        commentService.deleteComment(id);
    }




}
