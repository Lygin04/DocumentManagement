package com.lygin.documentmanagement.services.impl;

import com.lygin.documentmanagement.entity.DocType;
import com.lygin.documentmanagement.entity.Document;
import com.lygin.documentmanagement.repositories.DocumentRepository;
import com.lygin.documentmanagement.services.DocumentService;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository _documentRepository;
    private final ServletContext context;

    @Override
    public List<Document> GetAll() {
        return _documentRepository.findAll();
    }

    @Override
    public List<Document> Filter(String name, Date createDate, DocType type) {
        List<Document> documents = _documentRepository.findByNameAndCreateDateAndType(name, createDate, type);
        return documents;
    }

    @Override
    public List<Document> Filter(String name) {
        List<Document> documents = new LinkedList<>();
        if(name != null && !name.isEmpty())
            documents = _documentRepository.findAllByName(name);
        else
            documents = GetAll();
        return documents;
    }

    @Override
    public List<Document> Filter(DocType type) {
        List<Document> documents = _documentRepository.findAllByType(type);
        return documents;
    }

    @Override
    public boolean Delete(Long id) {
        Optional<Document> document = _documentRepository.findById(id);

        if (document.isPresent()) {
            _documentRepository.deleteById(id);
            File file = new File(Paths.get("src/main/resources/documents/").toAbsolutePath().normalize()
                    + "/" + document.get().getFullName());
            return file.delete();
        }
        return false;
    }

    @Override
    public ResponseEntity<Document> Create(MultipartFile file) throws IOException {

        Document document = new Document();
        if (file != null) {
            if(_documentRepository.findByName(file.getOriginalFilename()).isPresent())
                return ResponseEntity.badRequest().body(null);
            Path fileStorageLocation = Paths.get("src/main/resources/documents").toAbsolutePath().normalize();
                Files.createDirectories(fileStorageLocation);

            String fileName = UUID.randomUUID().toString() + "." + file.getOriginalFilename();


                Path targetLocation = fileStorageLocation.resolve(fileName);
                Files.copy(file.getInputStream(), targetLocation);


            document.setName(file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")));
            document.setFullName(fileName);
            document.setPath("/resources/documents/" + fileName);
            document.setCreateDate(new Date());
            String type = file.getContentType().substring(file.getContentType().lastIndexOf("/") + 1);
            if (Objects.equals(type, "msword"))
                document.setType(DocType.doc);
            else if(Objects.equals(type, "csv"))
                document.setType(DocType.csv);
            else if (Objects.equals(type, "txt"))
                document.setType(DocType.txt);
            else if (Objects.equals(type, "pdf"))
                document.setType(DocType.pdf);
            else
                document.setType(DocType.non);
            _documentRepository.save(document);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(document);
    }
}
