package com.apsi.repo.user.dto;

import lombok.Data;

@Data
public class UpdateUserPasswordDto {
    private Long id;
    private String username;
    private String password;
    private String newPassword;
}
