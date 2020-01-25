package com.apsi.repo.tests.service;

import com.apsi.repo.tests.domain.Test;
import com.apsi.repo.tests.domain.TestStatus;
import com.apsi.repo.tests.dto.TestDto;

import java.util.List;

public interface TestsService {
    List<Test> getAcceptedTests();

    List<Test> getAllAcceptedTests();

    Test createTest(TestDto dto);

    Test updateTest(Test toUpdate);

    List<TestStatus> getTestStatuses();

    Test getTest(Long id);

    List<Test> getTestsToAccept();

    Test acceptTest(Long testId);
}
