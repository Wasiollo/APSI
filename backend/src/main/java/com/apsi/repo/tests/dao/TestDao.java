package com.apsi.repo.tests.dao;

import com.apsi.repo.exception.TestNotFoundException;
import com.apsi.repo.tests.domain.Test;
import com.apsi.repo.user.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TestDao extends CrudRepository<Test, Long> {
    @Query("FROM Test t WHERE t.accepted = true AND t.owner = :user")
    List<Test> findAllAcceptedByOwner(User user);

    List<Test> findAllByAccepted(Boolean accepted);

    default void deleteOrThrow(Long testId) {
        deleteById(findByIdOrThrow(testId).getId());
    }

    default Test findByIdOrThrow(Long testId) {
        return findById(testId).orElseThrow(() -> new TestNotFoundException(testId));
    }
}
