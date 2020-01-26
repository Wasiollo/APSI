package com.apsi.repo.tests.dto;

import com.apsi.repo.tests.domain.TestStatus;
import com.apsi.repo.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class TestInfoDto {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime updateDate;
    private TestStatus status;
    private Boolean accepted;
    private User owner;
    private List<SpecificationDto> specification;
    private List<DocumentInfoDto> documents;
}
