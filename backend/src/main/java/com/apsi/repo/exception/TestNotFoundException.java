package com.apsi.repo.exception;

public class TestNotFoundException extends RuntimeException {

    public TestNotFoundException(Long testId) {
        super(String.format("Test with id: %d not found", testId));
    }
}
