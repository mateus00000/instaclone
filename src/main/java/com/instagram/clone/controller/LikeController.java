package com.instagram.clone.controller;

import com.instagram.clone.dto.LikeDTO;
import com.instagram.clone.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts/{postId}/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping
    public ResponseEntity<?> addLike(@PathVariable Long postId, @RequestHeader("username") String username) {
        LikeDTO likeDTO = new LikeDTO();
        likeDTO.setPostId(postId);
        likeDTO.setUsername(username);

        try {
            if (likeService.addLike(likeDTO)) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body("Like already exists");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> removeLike(@PathVariable Long postId, @RequestHeader("username") String username) {
        LikeDTO likeDTO = new LikeDTO();
        likeDTO.setPostId(postId);
        likeDTO.setUsername(username);

        try {
            if (likeService.removeLike(likeDTO)) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body("Like not found");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/check-like")
    public ResponseEntity<Boolean> checkIfUserLikedPost(@PathVariable Long postId,
            @RequestHeader("username") String username) {
        boolean liked = likeService.checkIfUserLikedPost(username, postId);
        return ResponseEntity.ok(liked);
    }
}
