package com.apsi.repo.tests.dto;

import lombok.Data;

@Data
public class DocumentInfoDto {

    private Long id;
    private String filename;

    public DocumentInfoDto(Long id, String filename) {
        this.id = id;
        this.filename = filename;
    }
}
