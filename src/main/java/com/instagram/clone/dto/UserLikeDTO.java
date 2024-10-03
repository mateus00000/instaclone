package com.instagram.clone.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLikeDTO {

    private Long id;
    private String username;
    private String profilePicture;
}
