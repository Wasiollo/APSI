package com.apsi.repo.user.dto;

import lombok.Data;

@Data
public class RegisterUserDto {
    private String username;
    private String password;
    private String email;
}
