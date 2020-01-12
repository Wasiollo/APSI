package com.apsi.repo.security;

import com.apsi.repo.security.domain.AuthToken;
import com.apsi.repo.security.utils.JwtTokenUtil;
import com.apsi.repo.user.domain.User;
import com.apsi.repo.user.dto.LoginUser;
import com.apsi.repo.user.dto.RegisterUserDto;
import com.apsi.repo.user.dto.UserDto;
import com.apsi.repo.user.exception.NoSuchUserException;
import com.apsi.repo.user.exception.UserByMailExistsException;
import com.apsi.repo.user.exception.UserExistsException;
import com.apsi.repo.user.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.support.SecurityWebApplicationContextUtils;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private Logger logger = LogManager.getLogger(AuthenticationController.class);

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @PostMapping(value = "/authenticate")
    public AuthToken generateToken(@RequestBody LoginUser loginUser) throws NoSuchUserException {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
        final User user = userService.getUserToAuthentication(loginUser.getUsername()).orElseThrow(NoSuchUserException::new);
        final String token = jwtTokenUtil.generateToken(user);

        logger.info(String.format("%s authenticated.", loginUser.getUsername()));

        return new AuthToken(token, user.getUsername(), user.getId());
    }

    @PostMapping(value = "/signup")
    @ResponseStatus(code = HttpStatus.CREATED)
    public RegisterUserDto registerUser(@RequestBody RegisterUserDto registerUser) throws UserExistsException, UserByMailExistsException {
        return userService.registerUser(registerUser);
    }

    @GetMapping(value = "/checkAdminRole/{token}")
    public Boolean checkAdminRole(@PathVariable String token) {
        return jwtTokenUtil.tokenHasRole(token, "ROLE_ADMIN");
    }

    @PostMapping(value = "/renew")
    @ResponseStatus(code = HttpStatus.OK)
    public AuthToken renewToken(@RequestBody String currentToken) throws NoSuchUserException {
        String renewedToken = jwtTokenUtil.renewToken(currentToken);

        UserDto user = userService.getUserByUserName(jwtTokenUtil.getUsernameFromToken(renewedToken));
        return new AuthToken(renewedToken, user.getUsername() ,user.getId() ) ;
    }

    @GetMapping(value = "/current")
    @ResponseStatus(code = HttpStatus.OK)
    public UserDto getCurrentUser() throws NoSuchUserException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if(principal instanceof UserDetails){
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        return userService.getUserByUserName(username);
    }
}
