package com.apsi.repo.tests.domain;

import com.apsi.repo.tests.dto.DocumentDto;
import com.apsi.repo.tests.dto.DocumentInfoDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "documents")
@Data
@NoArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String filename;
    @Lob
    @Column(length = 10000000)
    private String data;

    public Document(DocumentDto dto) {
        filename = dto.getFilename();
        data = dto.getData();
    }

    public DocumentInfoDto toInfoDto() {
        return new DocumentInfoDto(id, filename);
    }
}
