package com.esprit.pidev.RestController.ForumController;

import com.esprit.pidev.entities.Forum.Comment;
import com.esprit.pidev.entities.Forum.Post;
import com.esprit.pidev.entities.Forum.Tag;
import com.esprit.pidev.services.ForumServices.IComment;
import com.esprit.pidev.services.ForumServices.IPost;
import com.esprit.pidev.services.ForumServices.ITag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
@AllArgsConstructor

public class TagController {
      ITag iTag;

     IPost iPost;

     IComment iComment;

    @PostMapping("/addTag")
    public Tag addTag(@RequestBody Tag tag) {
        return iTag.addTag(tag);
    }

    @PostMapping("/addTagToPost")
    public Post addTagToPost(@RequestParam Long postId, @RequestParam Long tagId) {
        Post post = iPost.retrievePostById(postId);
        Tag tag = iTag.retrieveTagById(tagId);

        post.getTags().add(tag);
        iPost.addPost(post);

        return post;
    }


    @PostMapping("/addTagToComment")
    public Comment addTagToComment(@RequestParam Long commentId, @RequestParam Long tagId, @RequestBody Comment comment) {
        Tag tag = iTag.retrieveTagById(tagId);

        comment.getTags().add(tag);
        iComment.updateComment(commentId, comment);

        return comment;
    }

}
