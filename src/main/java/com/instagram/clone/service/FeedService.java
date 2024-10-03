package com.instagram.clone.service;

import com.instagram.clone.dto.FeedPostDTO;
import com.instagram.clone.model.Post;
import com.instagram.clone.repository.PostRepository;
import com.instagram.clone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public List<FeedPostDTO> getFeedForUserId(Long userId) {
        List<Long> userIdsFollowed = userRepository.findUserIdsFollowedBy(userId);
        List<Object[]> results = postRepository.findAllWithLikesAndCommentsByUserIdIn(userIdsFollowed);
        return results.stream().map(this::convertToFeedPostDTO).collect(Collectors.toList());
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
}