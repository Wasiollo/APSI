package com.apsi.repo.security.utils;

import com.apsi.repo.user.dao.UserDao;
import com.apsi.repo.user.domain.User;
import com.apsi.repo.user.domain.UserRole;
import com.apsi.repo.user.exception.NoSuchUserException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;

import static com.apsi.repo.security.domain.SecurityConstants.ACCESS_TOKEN_VALIDITY_SECONDS;
import static com.apsi.repo.security.domain.SecurityConstants.SIGNING_KEY;

@Component
public class JwtTokenUtil implements Serializable {

    private final UserDao userDao;

    private static final String SCOPES = "scopes";

    private Logger logger = LogManager.getLogger(JwtTokenUtil.class);

    @Autowired
    public JwtTokenUtil(UserDao userDao) {
        this.userDao = userDao;
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Boolean tokenHasRole(String token, String role) {
        final List<LinkedHashMap> scopesList = getAllClaimsFromToken(token).get(SCOPES, List.class);

        return scopesList
                .stream()
                .filter(x -> x.containsValue(role))
                .findFirst().orElse(null) != null;


    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(User user) throws NoSuchUserException {
        return doGenerateToken(user.getUsername());
    }

    private String doGenerateToken(String subject) throws NoSuchUserException {

        Claims claims = Jwts.claims().setSubject(subject);
        User user = userDao.findByUsername(subject).orElseThrow(NoSuchUserException::new);

        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        for(UserRole role : user.getUserRoles())
            roles.add(new SimpleGrantedAuthority(role.getRoleName()));
        claims.put(SCOPES, roles);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("repo.apsi.com")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (
                username.equals(userDetails.getUsername())
                        && !isTokenExpired(token));
    }

    public String renewToken(String oldToken) throws NoSuchUserException {
            return doGenerateToken(this.getUsernameFromToken(oldToken));
    }

}
