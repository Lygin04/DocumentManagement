package com.lygin.documentmanagement.services;

import com.lygin.documentmanagement.entity.DocType;
import com.lygin.documentmanagement.entity.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface DocumentService {

    List<Document> GetAll();
    List<Document> Filter(String name);
    List<Document> Filter(DocType type);
    boolean Delete(Long id);
    ResponseEntity<?> Create(MultipartFile file) throws IOException;
}
