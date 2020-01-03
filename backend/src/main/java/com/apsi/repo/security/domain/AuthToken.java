package com.apsi.repo.security.domain;

import lombok.Data;

@Data
public class AuthToken {

    private final String token;
    private final String username;
    private final Long userId;

}
