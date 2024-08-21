package com.esprit.pidev.services.ForumServices;

import com.esprit.pidev.entities.Forum.Comment;
import com.esprit.pidev.entities.Forum.Post;

import java.util.List;

public interface IComment {

    Comment addComment(Comment comment,Long postId);

   /* void likeComment(User user, Long commentId);

    void unlikeComment(User user, Long commentId);*/
    Comment updateComment(Long id,Comment comment);
    Comment retrieveCommentById(Long id);
    Comment addReply(Long commentId, Comment reply);
    List<Comment> retrieveAllComment();
    void deleteComment(Long id);
    List<Comment> retrieveCommentsByPost(Post post);

}
