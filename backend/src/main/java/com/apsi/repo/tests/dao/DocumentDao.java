package com.apsi.repo.tests.dao;

import com.apsi.repo.tests.domain.Document;
import org.springframework.data.repository.CrudRepository;

public interface DocumentDao extends CrudRepository<Document, Long> {
}
