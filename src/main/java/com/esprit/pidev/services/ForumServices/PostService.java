package com.esprit.pidev.services.ForumServices;

import com.esprit.pidev.entities.Forum.Category;
import com.esprit.pidev.entities.Forum.Post;
import com.esprit.pidev.entities.UserRole.User;
import com.esprit.pidev.repository.ForumRepository.PostRepository;
import com.esprit.pidev.repository.UserRoleRepository.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PostService implements IPost {

    PostRepository postRepository;
    UserRepository userRepository;


    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }








    //method to upload an image to a post
    @Override
    public ResponseEntity<Void> uploadImageToAPost(Long id, MultipartFile image) throws IOException {
        Post post = postRepository.findById(id).orElseThrow(() ->  new NoSuchElementException("Post not found"));
        post.setImageData(image.getBytes());
        post.setImageType(image.getContentType()); // set the image type
        // set the image path based on your requirements
        String imagePath = "images" + UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(image.getOriginalFilename());
        post.setImagePath(imagePath);
        postRepository.save(post);
        Path directory = Paths.get("images");
        if (!Files.exists(directory)) {Files.createDirectories(directory);}
        Path filePath = directory.resolve(imagePath);
        Files.write(filePath, image.getBytes());
        return ResponseEntity.ok().build();

    }


//method to get the image by the post ID
    public byte[] getImageDataById(Long id) throws IOException {
        Post post = postRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Post not found"));
        return post.getImageData();
    }

    @Override
    public Post addPostAndImage(String title, String description, MultipartFile image) throws IOException {

        Post pt = new Post();
        pt.setTitle(title);
        pt.setContent(description);
        byte[] imageData = image.getBytes();
        pt.setImageData(imageData);
        // Save the image file to a folder named 'images' in your project directory
        Path directory = Paths.get("images");
        if (!Files.exists(directory)) {Files.createDirectories(directory);}
        Path imagePath = directory.resolve(UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(image.getOriginalFilename()));
        Files.write(imagePath, imageData);
        postRepository.save(pt);
        return pt;
    }

    @Override
    public Post updatePost(Long id, Post pt) {
        User user = getCurrentUser();
        pt.setUser(user);
        Post existingPost = postRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Post with id " + id + " not found"));
        existingPost.setTitle(pt.getTitle());
        existingPost.setContent(pt.getContent());
        existingPost.setUser(pt.getUser());
        existingPost.setComments(pt.getComments());
        return postRepository.save(existingPost);
    }


    @Override
    public Post addPost(Post pt) {
        User user = getCurrentUser();
        pt.setUser(user);
        postRepository.save(pt);
        return pt;
    }


    @Override
    public Post retrievePostById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Post with id " + id + " not found"));
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> retrieveAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
