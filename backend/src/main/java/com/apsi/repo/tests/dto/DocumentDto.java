package com.apsi.repo.tests.dto;

import lombok.Data;

@Data
public class DocumentDto {

    private Long testId;
    private String filename;
    private byte[] data;
}
