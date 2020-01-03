package com.apsi.repo.user.config;

import com.apsi.repo.user.dao.RoleDao;
import com.apsi.repo.user.domain.UserRole;
import com.apsi.repo.user.dto.UserDto;
import com.apsi.repo.user.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.apsi.repo.user.domain.User;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Configuration
public class RolesConfig {
    public static final String ADMIN_ROLE = "ROLE_ADMIN";

    private static final List<String> ROLES = List.of(ADMIN_ROLE);

    @Bean
    CommandLineRunner init(RoleDao roleDao, UserService userService) {
        return args -> {
            if (StreamSupport.stream(roleDao.findAll().spliterator(), false).count() < ROLES.size()) {
                for (String role : ROLES) {
                    UserRole newRole = new UserRole();
                    newRole.setRoleName(role);
                    roleDao.save(newRole);
                }
            }
            UserDto adminUser = new UserDto("admin", "admin", "admin@admin.pl");
            adminUser.setUserRoles(StreamSupport.stream(roleDao.findAll().spliterator(), false).collect(Collectors.toList()));
            User savedAdminUser = userService.save(adminUser);
            System.out.println(savedAdminUser);

        };
    }
}
