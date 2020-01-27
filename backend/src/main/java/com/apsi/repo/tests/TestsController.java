package com.apsi.repo.tests;

import com.apsi.repo.tests.domain.Document;
import com.apsi.repo.tests.domain.Test;
import com.apsi.repo.tests.domain.TestStatus;
import com.apsi.repo.tests.dto.DocumentDto;
import com.apsi.repo.tests.dto.DocumentInfoDto;
import com.apsi.repo.tests.dto.TestDto;
import com.apsi.repo.tests.dto.TestInfoDto;
import com.apsi.repo.tests.service.TestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/tests")
public class TestsController {

    private final TestsService testsService;

    @Autowired
    public TestsController(TestsService testsService) {
        this.testsService = testsService;
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    @Secured({"ROLE_TESTER", "ROLE_TEST_LEADER"})
    public List<TestInfoDto> getTests() {
        return testsService.getAcceptedTests();
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    @Secured({"ROLE_TESTER", "ROLE_TEST_LEADER"})
    public TestInfoDto getTest(@PathVariable Long id) {
        return testsService.getTest(id);
    }

    @GetMapping("/statuses")
    @ResponseStatus(code = HttpStatus.OK)
    @Secured({"ROLE_TESTER", "ROLE_TEST_LEADER"})
    public List<TestStatus> getTestStatuses() {
        return testsService.getTestStatuses();
    }

    @GetMapping("/all")
    @ResponseStatus(code = HttpStatus.OK)
    @Secured({"ROLE_TESTER", "ROLE_TEST_LEADER"})
    public List<TestInfoDto> getAllTests() {
        return testsService.getAllAcceptedTests();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Secured({"ROLE_TEST_SCENARIO_CREATOR", "ROLE_TEST_LEADER"})
    public TestInfoDto createTest(@RequestBody TestDto dto) {
        return testsService.createTest(dto);
    }

    @PutMapping
    @ResponseStatus(code = HttpStatus.OK)
    @Secured({"ROLE_TESTER", "ROLE_TEST_LEADER"})
    public TestInfoDto updateTestStatus(@RequestBody Test toUpdate) {
        return testsService.updateTestStatus(toUpdate);
    }

    @GetMapping("/unaccepted")
    @ResponseStatus(code = HttpStatus.OK)
    @Secured("ROLE_TEST_LEADER")
    public List<TestInfoDto> getTestToAccept() {
        return testsService.getTestsToAccept();
    }

    @PostMapping("/accept/{testId}")
    @ResponseStatus(code = HttpStatus.OK)
    @Secured("ROLE_TEST_LEADER")
    public TestInfoDto acceptTest(@PathVariable Long testId) {
        return testsService.acceptTest(testId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    @Secured({"ROLE_TESTER", "ROLE_TEST_LEADER"})
    public void deleteTest(@PathVariable Long id) {
        testsService.deleteTest(id);
    }

    @PostMapping("/{id}/documents")
    @ResponseStatus(code = HttpStatus.OK)
    @Secured({"ROLE_TESTER", "ROLE_TEST_LEADER"})
    public List<DocumentInfoDto> createDocument(@PathVariable Long id, @RequestBody List<DocumentDto> dtos) {
        return testsService.createDocuments(id, dtos);
    }

    @DeleteMapping("/{id}/documents/{documentId}")
    @ResponseStatus(code = HttpStatus.OK)
    @Secured({"ROLE_TESTER", "ROLE_TEST_LEADER"})
    public void deleteDocument(@PathVariable Long id, @PathVariable Long documentId) {
        testsService.deleteDocument(id, documentId);
    }

    @GetMapping("/{id}/documents/{documentId}")
    @ResponseStatus(code = HttpStatus.OK)
    @Secured({"ROLE_TESTER", "ROLE_TEST_LEADER"})
    public Document getDocument(@PathVariable Long id, @PathVariable Long documentId) {
        return testsService.getDocument(documentId);
    }
}
