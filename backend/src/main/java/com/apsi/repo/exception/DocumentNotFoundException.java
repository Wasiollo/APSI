package com.apsi.repo.exception;

public class DocumentNotFoundException extends RuntimeException {

    public DocumentNotFoundException(Long documentId) {
        super(String.format("Document with id: %d not found", documentId));
    }
}

