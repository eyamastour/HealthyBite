package com.esprit.pidev.services.ForumServices;

import com.esprit.pidev.entities.Forum.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;

public interface IPost {
    Post addPost(Post pt);
    Post updatePost(Long id, Post pt);
    Post addPostAndImage(String title, String description, MultipartFile image)throws IOException;
    ResponseEntity<Void> uploadImageToAPost(Long id, MultipartFile image) throws IOException;
    Post retrievePostById(Long id);
    List<Post> retrieveAllPosts();
    void deletePost(Long id);
    List<Post> findAll();
}
