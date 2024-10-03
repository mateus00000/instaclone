package com.instagram.clone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.instagram.clone.dto.FeedPostDTO;
import com.instagram.clone.service.PostService;

@RestController
@RequestMapping("/p")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/{postId}")
    public ResponseEntity<FeedPostDTO> getPostById(@PathVariable Long postId) {
        FeedPostDTO postDetail = postService.getPostById(postId);
        return ResponseEntity.ok(postDetail);
    }
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}

