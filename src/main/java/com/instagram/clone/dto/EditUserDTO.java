package com.instagram.clone.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EditUserDTO {

    private String username;
    private String bio;
    private String profilePicture;
    private String token;  
}
