package com.instagram.clone.dto;

public class UserProfileDTO {
    private Long id;
    private String username;
    private String bio;
    private String profilePicture;
    private Long followersCount;
    private Long followingCount;

    // Construtor completo
    public UserProfileDTO(Long id, String username, String bio, String profilePicture, Long followersCount,
            Long followingCount) {
        this.id = id;
        this.username = username;
        this.bio = bio;
        this.profilePicture = profilePicture;
        this.followersCount = followersCount;
        this.followingCount = followingCount;
    }

    // Getters e setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Long getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(Long followersCount) {
        this.followersCount = followersCount;
    }

    public Long getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(Long followingCount) {
        this.followingCount = followingCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
