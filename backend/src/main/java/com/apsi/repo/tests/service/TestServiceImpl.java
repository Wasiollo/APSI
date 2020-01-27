package com.apsi.repo.tests.service;

import com.apsi.repo.exception.DocumentNotFoundException;
import com.apsi.repo.tests.dao.DocumentDao;
import com.apsi.repo.tests.dao.TestDao;
import com.apsi.repo.tests.domain.Document;
import com.apsi.repo.tests.domain.Test;
import com.apsi.repo.tests.domain.TestStatus;
import com.apsi.repo.tests.dto.DocumentDto;
import com.apsi.repo.tests.dto.DocumentInfoDto;
import com.apsi.repo.tests.dto.TestDto;
import com.apsi.repo.tests.dto.TestInfoDto;
import com.apsi.repo.user.domain.User;
import com.apsi.repo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class TestServiceImpl implements TestsService {

    private final TestDao testDao;
    private final DocumentDao documentDao;
    private final UserService userService;

    @Autowired
    public TestServiceImpl(TestDao testDao, DocumentDao documentDao, UserService userService) {
        this.testDao = testDao;
        this.documentDao = documentDao;
        this.userService = userService;
    }

    @Override
    public List<TestInfoDto> getAcceptedTests() {
        User currentUser = userService.getCurrentUser();
        return testDao.findAllAcceptedByOwner(currentUser).stream().map(Test::toTestInfoDto).collect(toList());
    }

    @Override
    public List<TestInfoDto> getAllAcceptedTests() {
        return testDao.findAllByAccepted(true).stream().map(Test::toTestInfoDto).collect(toList());
    }

    @Override
    public TestInfoDto createTest(TestDto dto) {
        Test toCreate = new Test(dto);
        toCreate.setStatus(TestStatus.NEW);
        toCreate.setUpdateDate(LocalDateTime.now());
        toCreate.setOwner(userService.getCurrentUser());
        return testDao.save(toCreate).toTestInfoDto();
    }

    @Override
    public TestInfoDto updateTestStatus(Test updateData) {
        Test updatedTest = testDao.findById(updateData.getId()).orElseThrow();
        updatedTest.setStatus(updateData.getStatus());
        updatedTest.setUpdateDate(LocalDateTime.now());
        return updatedTest.toTestInfoDto();
    }

    @Override
    public List<TestStatus> getTestStatuses() {
        return Arrays.asList(TestStatus.values());
    }

    @Override
    public TestInfoDto getTest(Long id) {
        return testDao.findById(id).orElseThrow().toTestInfoDto();
    }

    @Override
    public List<TestInfoDto> getTestsToAccept() {
        return testDao.findAllByAccepted(false).stream().map(Test::toTestInfoDto).collect(toList());
    }

    @Override
    public TestInfoDto acceptTest(Long testId) {
        Test testToAccept = testDao.findById(testId).orElseThrow();
        testToAccept.setAccepted(true);
        return testToAccept.toTestInfoDto();
    }

    @Override
    public void deleteTest(Long testId) {
        testDao.deleteOrThrow(testId);
    }

    @Override
    public List<DocumentInfoDto> createDocuments(Long testId, List<DocumentDto> dtos) {
        List<Document> documents = dtos.stream()
                .map(documentDto -> documentDao.save(new Document(documentDto)))
                .collect(toList());
        testDao.findByIdOrThrow(testId).addDocuments(documents);
        return documents.stream().map(Document::toInfoDto).collect(toList());
    }

    @Override
    public void deleteDocument(Long testId, Long documentId) {
        testDao.findByIdOrThrow(testId).deleteDocument(documentId);
        documentDao.deleteById(documentId);
    }

    @Override
    public Document getDocument(Long documentId) {
        return documentDao.findById(documentId).orElseThrow(() -> new DocumentNotFoundException(documentId));
    }
}
