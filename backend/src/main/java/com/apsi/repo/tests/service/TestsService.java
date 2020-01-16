package com.apsi.repo.tests.service;

import com.apsi.repo.tests.domain.Test;

import java.util.List;

public interface TestsService {
    List<Test> getTests();

    List<Test> getAllTests();

    Test createTest(Test toCreate);
}
