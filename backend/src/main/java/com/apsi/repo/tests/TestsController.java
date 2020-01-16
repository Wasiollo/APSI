package com.apsi.repo.tests;

import com.apsi.repo.tests.domain.Test;
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
        return testsService.getTests();
    }

    @GetMapping("/all")
    @ResponseStatus(code = HttpStatus.OK)
    @Secured("ROLE_TEST_LEADER")
    public List<Test> getAllTests() {
        return testsService.getAllTests();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Secured({"ROLE_TESTER", "ROLE_TEST_LEADER"})
    public Test createTest(Test toCreate) {
        return testsService.createTest(toCreate);
    }
}
