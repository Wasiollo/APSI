package com.apsi.repo.user.service;

import com.apsi.repo.user.domain.UserRole;
import com.apsi.repo.user.dto.RegisterUserDto;
import com.apsi.repo.user.dto.UpdateUserPasswordDto;
import com.apsi.repo.user.dto.UserDto;
import com.apsi.repo.user.domain.User;
import com.apsi.repo.user.exception.BadPreviousPasswordException;
import com.apsi.repo.user.exception.NoSuchUserException;
import com.apsi.repo.user.exception.UserByMailExistsException;
import com.apsi.repo.user.exception.UserExistsException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDto> getAllUsers();

    List<User> findAllUsersByIds(List<Long> ids);

    UserDto getUser(Long userId) throws NoSuchUserException;

    UserDto getUserByUserName(String username) throws NoSuchUserException;

    Optional<User> getUserToAuthentication(String username);

    UserDto updateUser(UserDto userDto) throws NoSuchUserException;

    UpdateUserPasswordDto updatePassword(UpdateUserPasswordDto userDto) throws NoSuchUserException, BadPreviousPasswordException;

    RegisterUserDto registerUser(RegisterUserDto registerUser) throws UserExistsException, UserByMailExistsException;

    User save(UserDto user);

    UserDto grantAdmin(Long userId) throws NoSuchUserException;

    UserDto revokeAdmin(Long userId) throws NoSuchUserException;

    List<UserRole> getAllRoles();
}
