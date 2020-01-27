package com.apsi.repo.tests.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SpecificationDto {

    private int number;
    private String userAction;
    private String systemReaction;
}
