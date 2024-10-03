package com.instagram.clone.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.instagram.clone.dto.FeedPostDTO;
import com.instagram.clone.dto.PostDTO;
import com.instagram.clone.model.Post;
import com.instagram.clone.model.User;
import com.instagram.clone.repository.CommentRepository;
import com.instagram.clone.repository.LikeRepository;
import com.instagram.clone.repository.PostRepository;
import com.instagram.clone.repository.UserRepository;

import jakarta.transaction.Transactional;

import java.util.stream.Collectors;

import org.springframework.web.server.ResponseStatusException;

@Service
public class PostService {

        @Autowired
        private UserRepository userRepository;
        @Autowired
        private PostRepository postRepository;

        @Autowired
        private LikeRepository likeRepository;

        @Autowired
        private CommentRepository commentRepository;

        public void createPost(String username, PostDTO postDTO) throws Exception {
                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new Exception("User not found"));

                Post post = new Post();
                post.setCaption(postDTO.getCaption()); // Usa o getter para obter a legenda
                post.setImageUrl(postDTO.getImageUrl()); // Usa o getter para obter a URL da imagem

                post.setUser(user); // Associa o usuário ao post

                postRepository.save(post); // Salva o post no banco de dados
        }

        public List<FeedPostDTO> findPostsByUser(String username) {
                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "Target user not found"));

                List<Object[]> results = postRepository
                                .findAllWithLikesAndCommentsByUserIdIn(Collections.singletonList(user.getId()));

                List<FeedPostDTO> feedPosts = results.stream()
                                .map(this::convertToFeedPostDTO)
                                .collect(Collectors.toList());

                return feedPosts;
        }

        private FeedPostDTO convertToFeedPostDTO(Object[] tuple) {
                Post post = (Post) tuple[0];
                Long likeCount = (Long) tuple[1];
                Long commentCount = (Long) tuple[2];

                FeedPostDTO dto = new FeedPostDTO();
                dto.setPostId(post.getId());
                dto.setCaption(post.getCaption());
                dto.setImageUrl(post.getImageUrl());
                dto.setLikeCount(likeCount);
                dto.setCommentCount(commentCount);

                FeedPostDTO.UserSummaryDTO userSummary = new FeedPostDTO.UserSummaryDTO();
                userSummary.setUserId(post.getUser().getId());
                userSummary.setUsername(post.getUser().getUsername());
                userSummary.setProfilePictureUrl(post.getUser().getProfilePicture());

                dto.setUserSummary(userSummary);

                return dto;
        }

        public FeedPostDTO getPostById(Long postId) {
                Post post = postRepository.findById(postId)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

                // Contando likes e comentários manualmente
                long likeCount = likeRepository.findAll().stream().filter(like -> like.getPost().getId().equals(postId))
                                .count();
                long commentCount = commentRepository.findByPostId(postId).size();

                return convertToFeedPostDTO(new Object[] { post, likeCount, commentCount });
        }

        @Transactional
        public void deletePost(Long postId) {
                // Primeiro, deletar todos os likes associados ao post
                likeRepository.deleteByPostId(postId);

                // Deletar todos os comentários associados ao post (se houver)
                commentRepository.deleteByPostId(postId);

                // Por último, deletar o post
                postRepository.deleteById(postId);
        }
}
