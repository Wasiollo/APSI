package com.apsi.repo.tests.service;

import com.apsi.repo.tests.dao.DocumentDao;
import com.apsi.repo.tests.dao.TestDao;
import com.apsi.repo.tests.domain.Test;
import com.apsi.repo.tests.domain.TestStatus;
import com.apsi.repo.tests.dto.DocumentDto;
import com.apsi.repo.tests.dto.TestDto;
import com.apsi.repo.user.domain.User;
import com.apsi.repo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
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
    public List<Test> getAcceptedTests() {
        User currentUser = userService.getCurrentUser();
        return testDao.findAllAcceptedByOwner(currentUser);
    }

    @Override
    public List<Test> getAllAcceptedTests() {
        return testDao.findAllByAccepted(true);
    }

    @Override
    public Test createTest(TestDto dto) {
        Test toCreate = new Test(dto);
        toCreate.setStatus(TestStatus.NEW);
        toCreate.setUpdateDate(LocalDateTime.now());
        toCreate.setOwner(userService.getCurrentUser());
        return testDao.save(toCreate);
    }

    @Override
    public Test updateTest(Test toUpdate) {
        toUpdate.setUpdateDate(LocalDateTime.now());
        return testDao.save(toUpdate);
    }

    @Override
    public List<TestStatus> getTestStatuses() {
        return Arrays.asList(TestStatus.values());
    }

    @Override
    public Test getTest(Long id) {
        return testDao.findById(id).orElseThrow();
    }

    @Override
    public List<Test> getTestsToAccept() {
        return testDao.findAllByAccepted(false);
    }

    @Override
    @Transactional
    public Test acceptTest(Long testId) {
        Test testToAccept = testDao.findById(testId).orElseThrow();
        testToAccept.setAccepted(true);
        return testToAccept;
    }

    @Override
    public void deleteTest(Long testId) {
        testDao.deleteOrThrow(testId);
    }

    @Override
    public void createDocument(Long testId, DocumentDto dto) {
        testDao.findByIdOrThrow(testId).addDocument(dto);
    }

    @Override
    public void deleteDocument(Long documentId) {
        documentDao.deleteById(documentId);
    }
}
