package com.instagram.clone.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class FeedPostDTO {
    private Long postId;
    private String caption;
    private String imageUrl;
    private Long likeCount;
    private Long commentCount;
    private UserSummaryDTO userSummary;

    // Getters e Setters
    @Data
    @NoArgsConstructor
    public static class UserSummaryDTO {
        private Long userId;
        private String username;
        private String profilePictureUrl;

        // Getters e Setters
    }
}
