package com.apsi.repo.user;

import com.apsi.repo.user.domain.UserRole;
import com.apsi.repo.user.dto.RegisterUserDto;
import com.apsi.repo.user.dto.UpdateUserPasswordDto;
import com.apsi.repo.user.dto.UserDto;
import com.apsi.repo.user.exception.BadPreviousPasswordException;
import com.apsi.repo.user.exception.NoSuchUserException;
import com.apsi.repo.user.exception.UserByMailExistsException;
import com.apsi.repo.user.exception.UserExistsException;
import com.apsi.repo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public RegisterUserDto addUser(@RequestBody RegisterUserDto user) throws UserExistsException, UserByMailExistsException {
        return userService.registerUser(user);
    }

    @GetMapping("/roles")
    @ResponseStatus(code = HttpStatus.OK)
    public List<UserRole> getAllRoles(){
        return userService.getAllRoles();
    }

    @PutMapping
    public UserDto updateUser(@RequestBody UserDto user) throws NoSuchUserException {
        return userService.updateUser(user);
    }

    @PutMapping("/update_password")
    public UpdateUserPasswordDto updatePassword(@RequestBody UpdateUserPasswordDto userDto) throws NoSuchUserException, BadPreviousPasswordException {
        return userService.updatePassword(userDto);
    }

    @GetMapping(value = "/list")
    public List<UserDto> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping(value = "/{userId}")
    public UserDto getUser(@PathVariable Long userId) throws NoSuchUserException {
        return userService.getUser(userId);
    }

    @GetMapping
    public UserDto getUserByUsername(@RequestParam(name = "username") String username) throws NoSuchUserException {
        return userService.getUserByUserName(username);
    }

    @PutMapping("/grant-admin")
    public UserDto grantAdmin(@RequestParam(name = "userId") Long userId) throws NoSuchUserException {
        return userService.grantAdmin(userId);
    }

    @PutMapping("/revoke-admin")
    public UserDto revokeAdmin(@RequestParam(name = "userId") Long userId) throws NoSuchUserException {
        return userService.revokeAdmin(userId);
    }
}
