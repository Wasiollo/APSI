package com.apsi.repo.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.apsi.repo.user.domain.UserRole;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Integer points;
    private List<UserRole> userRoles;

    public UserDto(String username, String password, String email) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
