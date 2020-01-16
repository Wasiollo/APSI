package com.apsi.repo.tests.service;

import com.apsi.repo.tests.dao.TestDao;
import com.apsi.repo.tests.domain.Test;
import com.apsi.repo.user.domain.User;
import com.apsi.repo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestsService {

    private final TestDao testDao;
    private final UserService userService;

    @Autowired
    public TestServiceImpl(TestDao testDao, UserService userService) {
        this.testDao = testDao;
        this.userService = userService;
    }

    @Override
    public List<Test> getTests() {
        User currentUser = userService.getCurrentUser();
        return testDao.findAllByOwner(currentUser);
    }

    @Override
    public List<Test> getAllTests() {
        return (List<Test>) testDao.findAll();
    }

    @Override
    public Test createTest(Test toCreate) {
        //TODO creation
        return null;
    }
}
