package com.instagram.clone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instagram.clone.dto.CommentDTO;
import com.instagram.clone.model.Comment;
import com.instagram.clone.model.Post;
import com.instagram.clone.model.User;
import com.instagram.clone.repository.CommentRepository;
import com.instagram.clone.repository.PostRepository;
import com.instagram.clone.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public CommentDTO createComment(CommentDTO commentDTO) {
        Optional<Post> post = postRepository.findById(commentDTO.getPostId());
        Optional<User> user = userRepository.findByUsername(commentDTO.getUsername());

        if (post.isPresent() && user.isPresent()) {
            Comment comment = new Comment();
            comment.setPost(post.get());
            comment.setUser(user.get());
            comment.setText(commentDTO.getContent());
            Comment savedComment = commentRepository.save(comment);

            CommentDTO responseDTO = new CommentDTO();
            responseDTO.setId(savedComment.getId());
            responseDTO.setPostId(savedComment.getPost().getId());
            responseDTO.setUsername(savedComment.getUser().getUsername());
            responseDTO.setContent(savedComment.getText());
            responseDTO.setCreatedAt(savedComment.getCreatedAt().toString());

            return responseDTO;
        } else {
            throw new RuntimeException("Post or User not found");
        }
    }

    public List<CommentDTO> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> {
            CommentDTO dto = new CommentDTO();
            dto.setId(comment.getId());
            dto.setPostId(comment.getPost().getId());
            dto.setUsername(comment.getUser().getUsername());
            dto.setContent(comment.getText());
            dto.setCreatedAt(comment.getCreatedAt().toString());
            return dto;
        }).toList();
    }

    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        
        commentRepository.delete(comment);
    }
}
