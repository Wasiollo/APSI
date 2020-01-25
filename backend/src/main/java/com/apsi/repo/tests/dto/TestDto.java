package com.apsi.repo.tests.dto;

import lombok.Data;

import java.util.List;

@Data
public class TestDto {
    private String name;
    private String description;
    private List<SpecificationDto> specifications;
}
