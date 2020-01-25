package com.apsi.repo.user.config;

import com.apsi.repo.user.dao.RoleDao;
import com.apsi.repo.user.dao.UserDao;
import com.apsi.repo.user.domain.User;
import com.apsi.repo.user.domain.UserRole;
import com.apsi.repo.user.dto.UserDto;
import com.apsi.repo.user.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RolesConfig {
    public static final UserRole ROLE_ADMIN = new UserRole("ROLE_ADMIN", "Administrator");
    public static final UserRole ROLE_USER = new UserRole("ROLE_USER", "User");
    public static final UserRole ROLE_TESTER = new UserRole("ROLE_TESTER", "Tester");
    public static final UserRole ROLE_TEST_LEADER = new UserRole("ROLE_TEST_LEADER", "Tests Leader");
    public static final UserRole ROLE_TEST_SCENARIO_CREATOR = new UserRole("ROLE_TEST_SCENARIO_CREATOR", "Scenario Creator");


    private static final List<UserRole> ROLES = List.of(ROLE_ADMIN, ROLE_USER, ROLE_TESTER, ROLE_TEST_LEADER, ROLE_TEST_SCENARIO_CREATOR);

    @Bean
    CommandLineRunner init(RoleDao roleDao, UserService userService, UserDao userDao) {
        return args -> {
            List<UserRole> rolesFromDb = (List<UserRole>) roleDao.findAll();
            ROLES.stream().filter(r ->
                    rolesFromDb.stream().noneMatch(dbr -> dbr.getRoleName().equals(r.getRoleName()))
            ).forEach(roleDao::save);

            List<UserRole> mainAdminRoles = List.of(
                    roleDao.findByRoleName(ROLE_ADMIN.getRoleName()).orElseThrow()
                    ,roleDao.findByRoleName(ROLE_USER.getRoleName()).orElseThrow()
                    ,roleDao.findByRoleName(ROLE_TESTER.getRoleName()).orElseThrow()
                    ,roleDao.findByRoleName(ROLE_TEST_SCENARIO_CREATOR.getRoleName()).orElseThrow()
            );

            User savedAdminUser;
            if (userDao.findByUsername("admin").isEmpty()) {
                UserDto adminUser = new UserDto("admin", "admin", "admin@admin.pl");
                adminUser.setUserRoles(mainAdminRoles);
                savedAdminUser = userService.save(adminUser);
            } else {
                UserDto existingAdmin = userService.getUserByUserName("admin");
                existingAdmin.setPassword("admin");
                existingAdmin.setUserRoles(mainAdminRoles);
                savedAdminUser = userService.save(existingAdmin);
            }
            System.out.println(savedAdminUser);

        };
    }
}
