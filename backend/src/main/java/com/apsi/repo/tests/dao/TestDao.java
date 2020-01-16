package com.apsi.repo.tests.dao;

import com.apsi.repo.tests.domain.Test;
import com.apsi.repo.user.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TestDao extends CrudRepository<Test, Long> {
    List<Test> findAllByOwner(User user);
}
