package com.apsi.repo.tests.domain;

import com.apsi.repo.user.domain.User;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tests")
@Data
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    @Enumerated(EnumType.STRING)
    private TestStatus status;
    @ManyToOne
    private User owner;
}
