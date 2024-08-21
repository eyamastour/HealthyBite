package com.esprit.pidev.RestController.ForumController;

import com.esprit.pidev.entities.Forum.Post;
import com.esprit.pidev.repository.ForumRepository.PostRepository;
import com.esprit.pidev.repository.UserRoleRepository.UserRepository;
import com.esprit.pidev.services.ForumServices.IPost;
import com.esprit.pidev.services.ForumServices.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.*;


@RequestMapping("/api/test")
@RestController
@AllArgsConstructor
public class PostController {

    IPost iPost;
    UserRepository userRepository;
    PostService postService;
    PostRepository postRepository;

    @GetMapping("/post/{id}/image")
    public ResponseEntity<byte[]> getImageData(@PathVariable Long id) throws IOException {
        byte[] imageData = postService.getImageDataById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(imageData.length);
        headers.setContentDisposition(ContentDisposition.attachment().filename("image.jpg").build());
        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }


    @PostMapping("/{id}/image")
    public ResponseEntity<Void> uploadImageToAPost(@PathVariable Long id, @RequestParam("image") MultipartFile image) throws IOException {
        return iPost.uploadImageToAPost(id, image);
    }

    @PostMapping("/addPostWithImg")
    public Post addPostAndImage(@RequestParam("title") String title, @RequestParam("content") String description, @RequestParam("image") MultipartFile image) throws IOException {
        return iPost.addPostAndImage(title, description, image);
    }

    @PostMapping("/addPost")
    public Post addPost(@RequestBody Post pt) {
        return iPost.addPost(pt);
    }


    @PutMapping("/updatePost/{id}")
    public Post updatePost(@PathVariable("id") Long id, @RequestBody Post pt) { return iPost.updatePost(id, pt); }

    @GetMapping("/getPostById/{id}")
    public Post retrievePostById(@PathVariable("id") Long id){
        return iPost.retrievePostById(id);
    }

    @GetMapping("/getAllPost")
    public List<Post> retrieveAllPost(){
        return iPost.findAll();
    }

    @GetMapping("/getAllPosts")
    @ResponseBody
    public List<Post> retrieveAllPosts(){
        return iPost.retrieveAllPosts();
    }

    @DeleteMapping("/deletePost/{id}")
    public void deletePost(@PathVariable("id") Long id){
        iPost.deletePost(id);
    }
}
