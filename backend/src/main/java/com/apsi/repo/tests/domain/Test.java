package com.apsi.repo.tests.domain;

import com.apsi.repo.tests.dto.DocumentDto;
import com.apsi.repo.tests.dto.TestDto;
import com.apsi.repo.tests.dto.TestInfoDto;
import com.apsi.repo.user.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User owner;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Specification> specifications;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Document> documents;

    public Test(TestDto dto) {
        name = dto.getName();
        description = dto.getDescription();
        if(dto.getSpecifications() != null) {
            specifications = dto.getSpecifications().stream()
                    .map(Specification::new)
                    .collect(toList());
        }
        if(dto.getDocuments() != null) {
            documents = dto.getDocuments().stream()
                    .map(Document::new)
                    .collect(toList());
        }

    }

    public TestInfoDto toTestInfoDto() {
        return new TestInfoDto(id, name, description, updateDate, status, accepted, owner,
                specifications.stream().map(Specification::toDto).collect(toList()),
                documents.stream().map(Document::toInfoDto).collect(toList()));
    }

    public void addDocument(DocumentDto dto) {
        documents.add(new Document(dto));
    }

    public void addDocuments(List<Document> documents) {
        this.documents.addAll(List.copyOf(documents));
    }

    public void deleteDocument(Long documentId) {
        List<Document> documents = this.documents.stream().filter(document -> !document.getId().equals(documentId)).collect(toList());
        this.documents.clear();
        this.documents = documents;
    }
}
