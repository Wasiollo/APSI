package com.apsi.repo.security.utils;

import com.apsi.repo.user.exception.NoSuchUserException;
import com.apsi.repo.user.exception.UserByMailExistsException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.apsi.repo.user.exception.UserExistsException;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(UserExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public String handleUserExistsException(){
        return "User with this username exists in application.";
    }

    @ExceptionHandler(UserByMailExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public String handleUserByMailExistsException(){
        return "User with this e-mail exists in application.";
    }

    @ExceptionHandler(NoSuchUserException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public String handleNoSuchUserException() {
        return "No such user";
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public String handleExpiredJwtException() {
        return "Token Expired";
    }

}
