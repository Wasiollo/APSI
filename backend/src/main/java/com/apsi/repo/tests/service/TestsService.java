package com.apsi.repo.tests.service;

import com.apsi.repo.tests.domain.Document;
import com.apsi.repo.tests.domain.Test;
import com.apsi.repo.tests.domain.TestStatus;
import com.apsi.repo.tests.dto.DocumentDto;
import com.apsi.repo.tests.dto.DocumentInfoDto;
import com.apsi.repo.tests.dto.TestDto;
import com.apsi.repo.tests.dto.TestInfoDto;

import java.io.File;
import java.util.List;

public interface TestsService {
    List<TestInfoDto> getAcceptedTests();

    List<TestInfoDto> getAllAcceptedTests();

    TestInfoDto createTest(TestDto dto);

    TestInfoDto updateTestStatus(Test toUpdate);

    List<TestStatus> getTestStatuses();

    TestInfoDto getTest(Long id);

    List<TestInfoDto> getTestsToAccept();

    TestInfoDto acceptTest(Long testId);

    void deleteTest(Long testId);

    List<DocumentInfoDto> createDocuments(Long testId, List<DocumentDto> dtos);

    void deleteDocument(Long id, Long documentId);

    Document getDocument(Long documentId);
}
