package com.lygin.documentmanagement.repositories;

import com.lygin.documentmanagement.entity.DocType;
import com.lygin.documentmanagement.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findByName(String name);
    List<Document> findAllByName(String name);
    List<Document> findAllByType(DocType type);
    List<Document> findByNameAndCreateDateAndType(String name, Date createDate, DocType type);
}
