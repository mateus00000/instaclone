package com.instagram.clone.service;

import com.instagram.clone.dto.LikeDTO;
import com.instagram.clone.model.Like;
import com.instagram.clone.model.Post;
import com.instagram.clone.model.User;
import com.instagram.clone.repository.LikeRepository;
import com.instagram.clone.repository.PostRepository;
import com.instagram.clone.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

        @Autowired
        private LikeRepository likeRepository;
        @Autowired
        private UserRepository userRepository;
        @Autowired
        private PostRepository postRepository;

        public boolean addLike(LikeDTO likeDTO) {
                User user = userRepository.findByUsername(likeDTO.getUsername())
                                .orElseThrow(() -> new RuntimeException("User not found"));
                Post post = postRepository.findById(likeDTO.getPostId())
                                .orElseThrow(() -> new RuntimeException("Post not found"));

                if (likeRepository.findByUserAndPost(user, post).isPresent()) {
                        return false; // Like já existe para esse usuário e post
                }

                Like like = new Like();
                like.setUser(user);
                like.setPost(post);
                likeRepository.save(like);
                return true;
        }

        public boolean removeLike(LikeDTO likeDTO) {
                User user = userRepository.findByUsername(likeDTO.getUsername())
                                .orElseThrow(() -> new RuntimeException("User not found"));
                Post post = postRepository.findById(likeDTO.getPostId())
                                .orElseThrow(() -> new RuntimeException("Post not found"));

                likeRepository.findByUserAndPost(user, post)
                                .ifPresent(likeRepository::delete);
                return true;
        }

        public boolean checkIfUserLikedPost(String username, Long postId) {
                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new RuntimeException("User not found"));
                Post post = postRepository.findById(postId)
                                .orElseThrow(() -> new RuntimeException("Post not found"));

                return likeRepository.findByUserAndPost(user, post).isPresent();
        }

        public List<User> getUsersWhoLikedPost(Long postId) {
                return likeRepository.findByPostId(postId).stream()
                                .map(like -> like.getUser())
                                .collect(Collectors.toList());
        }
}
