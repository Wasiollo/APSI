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
    public static final UserRole ROLE_ADMIN = new UserRole("ROLE_ADMIN", "Administrator");
    public static final UserRole ROLE_MOD = new UserRole("ROLE_MOD", "Moderator");
    public static final UserRole ROLE_EMPL = new UserRole("ROLE_EMPL", "Employee");
    public static final UserRole ROLE_USER = new UserRole("ROLE_USER", "User");


    private static final List<UserRole> ROLES = List.of(ROLE_ADMIN, ROLE_MOD, ROLE_EMPL, ROLE_USER);

    @Bean
    CommandLineRunner init(RoleDao roleDao, UserService userService) {
        return args -> {
            List<UserRole> rolesFromDb = (List<UserRole>) roleDao.findAll();
            ROLES.stream().filter(r ->
                    rolesFromDb.stream().noneMatch(dbr -> dbr.getRoleName().equals(r.getRoleName()))
            ).forEach(roleDao::save);

            UserDto adminUser = new UserDto("admin", "admin", "admin@admin.pl");
            adminUser.setUserRoles(List.of(
                    roleDao.findByRoleName(ROLE_ADMIN.getRoleName()).orElseThrow(),
                    roleDao.findByRoleName(ROLE_USER.getRoleName()).orElseThrow()
            ));
            User savedAdminUser = userService.save(adminUser);
            System.out.println(savedAdminUser);
        };
    }
}
