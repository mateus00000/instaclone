package com.instagram.clone.dto;

import lombok.Data;

@Data
public class ResetPasswordDTO {
    private String email;
    private String code;
    private String newPassword;
}