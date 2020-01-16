package com.apsi.repo.user.service;

import com.apsi.repo.user.config.RolesConfig;
import com.apsi.repo.user.dao.RoleDao;
import com.apsi.repo.user.dao.UserDao;
import com.apsi.repo.user.domain.User;
import com.apsi.repo.user.domain.UserRole;
import com.apsi.repo.user.dto.RegisterUserDto;
import com.apsi.repo.user.dto.UpdateUserPasswordDto;
import com.apsi.repo.user.dto.UserDto;
import com.apsi.repo.user.exception.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.apsi.repo.user.config.RolesConfig.ROLE_USER;

@Transactional
@Service(value = "userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserDao userDao;
    private final BCryptPasswordEncoder bcryptEncoder;
    private final RoleDao roleDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, BCryptPasswordEncoder bcryptEncoder, RoleDao roleDao) {
        this.userDao = userDao;
        this.bcryptEncoder = bcryptEncoder;
        this.roleDao = roleDao;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return StreamSupport.stream(userDao.findAll().spliterator(), false)
                .map(this::mapUserToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findAllUsersByIds(List<Long> ids) {
        return (List<User>) userDao.findAllById(ids);
    }

    @Override
    public UserDto getUser(Long userId) throws NoSuchUserException {
        return mapUserToDto(userDao.findById(userId).orElseThrow(NoSuchUserException::new));
    }

    @Override
    public UserDto getUserByUserName(String username) throws NoSuchUserException {
        return mapUserToDto(userDao.findByUsername(username).orElseThrow(NoSuchUserException::new));
    }

    @Override
    public Optional<User> getUserToAuthentication(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public UserDto updateUser(UserDto userDto) throws NoSuchUserException {
        User user = userDao.findById(userDto.getId()).orElseThrow(NoSuchUserException::new);
        BeanUtils.copyProperties(userDto, user, "password");
        userDao.save(user);

        return userDto;
    }

    @Override
    public User save(UserDto user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setUserRoles(user.getUserRoles());

        return userDao.save(newUser);
    }

    @Override
    public UserDto grantAdmin(Long userId) throws NoSuchUserException {
        User user = userDao.findById(userId).orElseThrow(NoSuchUserException::new);
        UserRole adminRole = roleDao.findByRoleName(RolesConfig.ROLE_ADMIN.getRoleName()).get();
        user.getUserRoles().add(adminRole);
        return mapUserToDto(user);
    }

    @Override
    public UserDto revokeAdmin(Long userId) throws NoSuchUserException {
        User user = userDao.findById(userId).orElseThrow(NoSuchUserException::new);
        UserRole adminRole = roleDao.findByRoleName(RolesConfig.ROLE_ADMIN.getRoleName()).get();
        user.getUserRoles().remove(adminRole);
        return mapUserToDto(user);
    }

    @Override
    public List<UserRole> getAllRoles() {
        return (List<UserRole>) roleDao.findAll();
    }

    @Override
    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if(principal instanceof UserDetails){
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return userDao.findByUsername(username).orElseThrow();
    }

    @Override
    public UpdateUserPasswordDto updatePassword(UpdateUserPasswordDto userDto) throws NoSuchUserException, BadPreviousPasswordException, SamePasswordException {
        User user = userDao.findById(userDto.getId()).orElseThrow(NoSuchUserException::new);
        if (bcryptEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new SamePasswordException();
        }
/*        if (bcryptEncoder.matches(userDto.getPassword(), user.getPassword())) { TODO add method to allow normal user change it's password with checking current password
            throw new BadPreviousPasswordException();
        }*/

        user.setPassword(bcryptEncoder.encode(userDto.getPassword()));
        userDao.save(user);
        return userDto;
    }

    @Override
    public RegisterUserDto registerUser(RegisterUserDto registerUser) throws UserExistsException, UserByMailExistsException {
        if (userDao.findByUsername(registerUser.getUsername()).isPresent()) {
            throw new UserExistsException();
        }
        if (userDao.findByEmail(registerUser.getEmail()).isPresent()){
            throw new UserByMailExistsException();
        }
        UserDto userToRegister = new UserDto(registerUser.getUsername(), registerUser.getPassword(), registerUser.getEmail());
        userToRegister.setUserRoles(List.of(ROLE_USER));
        this.save(userToRegister);

        return registerUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userDao.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Invalid username or password."));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    private List<SimpleGrantedAuthority> getAuthority(User user) {
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        for(UserRole role : user.getUserRoles())
            roles.add(new SimpleGrantedAuthority(role.getRoleName()));
        return roles;
    }

    public UserDto mapUserToDto(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto, "password");
        return userDto;
    }

}
