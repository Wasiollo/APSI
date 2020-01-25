package com.apsi.repo.tests;

import com.apsi.repo.tests.domain.Test;
import com.apsi.repo.tests.domain.TestStatus;
import com.apsi.repo.tests.dto.TestDto;
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
    public List<Test> getTests() {
        return testsService.getAcceptedTests();
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    @Secured({"ROLE_TESTER", "ROLE_TEST_LEADER"})
    public Test getTest(@PathVariable Long id) {
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
    @Secured("ROLE_TEST_LEADER")
    public List<Test> getAllTests() {
        return testsService.getAllAcceptedTests();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Secured({"ROLE_TEST_SCENARIO_CREATOR", "ROLE_TEST_LEADER"})
    public Test createTest(@RequestBody TestDto dto) {
        return testsService.createTest(dto);
    }

    @PutMapping
    @ResponseStatus(code = HttpStatus.OK)
    @Secured({"ROLE_TESTER", "ROLE_TEST_LEADER"})
    public Test updateTest(@RequestBody Test toUpdate) {
        return testsService.updateTest(toUpdate);
    }

    @GetMapping("/unaccepted")
    @ResponseStatus(code = HttpStatus.OK)
    @Secured("ROLE_TEST_LEADER")
    public List<Test> getTestToAccept(){
        return testsService.getTestsToAccept();
    }

    @PostMapping("/accept/{testId}")
    @ResponseStatus(code = HttpStatus.OK)
    @Secured("ROLE_TEST_LEADER")
    public Test acceptTest(@PathVariable Long testId){
        return testsService.acceptTest(testId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    @Secured({"ROLE_TESTER", "ROLE_TEST_LEADER"})
    public void deleteTest(@PathVariable Long testId) {
        testsService.deleteTest(testId);
    }

}
