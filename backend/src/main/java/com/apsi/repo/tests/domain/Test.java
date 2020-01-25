package com.apsi.repo.tests.domain;

import com.apsi.repo.tests.dto.TestDto;
import com.apsi.repo.user.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Entity
@Table(name = "tests")
@Data
@NoArgsConstructor
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private LocalDateTime updateDate;
    @Column
    @Enumerated(EnumType.STRING)
    private TestStatus status;
    @Column
    private Boolean accepted = false;

    @ManyToOne
    private User owner;

    @OneToMany
    private List<Specification> specifications;

    public Test(TestDto dto) {
        name = dto.getName();
        description = dto.getDescription();
        specifications = dto.getSpecifications().stream()
                .map(Specification::new)
                .collect(toList());
    }
}
