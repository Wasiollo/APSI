package com.apsi.repo.tests.domain;

import com.apsi.repo.tests.dto.SpecificationDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "specifications")
@Data
@NoArgsConstructor
public class Specification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int number;
    @Column
    private String userAction;
    @Column
    private String systemReaction;

    public Specification(SpecificationDto dto) {
        number = dto.getNumber();
        userAction = dto.getUserAction();
        systemReaction = dto.getSystemReaction();
    }

    public SpecificationDto toDto() {
        return new SpecificationDto(number, userAction, systemReaction);
    }
}
