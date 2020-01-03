package com.apsi.repo.user.dao;

import org.springframework.data.repository.CrudRepository;
import com.apsi.repo.user.domain.UserRole;

import java.util.Optional;

public interface RoleDao extends CrudRepository<UserRole, Long> {
    Optional<UserRole> findByRoleName(String roleName);
}
