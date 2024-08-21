package com.esprit.pidev.RestController.ForumController;

import com.esprit.pidev.entities.Forum.Category;
import com.esprit.pidev.entities.Forum.Post;
import com.esprit.pidev.services.ForumServices.ICategory;
import com.esprit.pidev.services.ForumServices.IPost;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/test")
public class CategoryController {

    ICategory iCategory;
    @Autowired
    IPost iPost;

    @GetMapping("/getAllPostsForPosts")
    @ResponseBody
    public List<Post> retrieveAllPosts(){
        List<Post> posts = iPost.retrieveAllPosts();

        return posts;
    }

    @GetMapping("/getPostByIdCateg/{id}")
    @ResponseBody
    public Post retrievePostById(@PathVariable("id") Long id) {
        Post post = iPost.retrievePostById(id);
        post.setContent(null);
        post.setLikes(null);
        post.setComments(null);
        return post;
    }

}
