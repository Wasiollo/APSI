package com.apsi.repo.security.domain;

public class SecurityConstants {
    private SecurityConstants(){}

    public static final Long ACCESS_TOKEN_VALIDITY_SECONDS = 10 * 60L;
    public static final String SIGNING_KEY = "apsi_repo";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
