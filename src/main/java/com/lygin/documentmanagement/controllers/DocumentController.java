package com.lygin.documentmanagement.controllers;

import com.lygin.documentmanagement.entity.DocType;
import com.lygin.documentmanagement.entity.Document;
import com.lygin.documentmanagement.services.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService _documentService;

    @GetMapping("/getAll")
    public String GetAll(Model model){
        List<Document> documents = _documentService.GetAll();
        model.addAttribute("documents", documents);
        model.addAttribute("documentType", DocType.values());
        return "documents-list";
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Document>> Filter(@RequestParam String name,
                                                 @RequestParam Date createDate,
                                                 @RequestParam DocType type){
        List<Document> documents = _documentService.Filter(name, createDate, type);
        return ResponseEntity.ok(documents);
    }

    @GetMapping("/filter/name")
    public String Filter(@RequestParam String name,
                                                 Model model){
        List<Document> documents = _documentService.Filter(name);
        model.addAttribute("documents", documents);
        model.addAttribute("documentType", DocType.values());
        return "documents-list";
    }

    @GetMapping("/filter/type")
    public String Filter(@RequestParam DocType type,
                         Model model){
        List<Document> documents = _documentService.Filter(type);
        model.addAttribute("documents", documents);
        model.addAttribute("documentType", DocType.values());
        return "documents-list";
    }

    @PostMapping("/add")
    public String Add(@RequestParam("file") MultipartFile file) throws IOException {

        _documentService.Create(file);
        return "redirect:getAll";
    }

    @GetMapping("/delete/{id}")
    public String Delete(@PathVariable Long id){
        boolean a = _documentService.Delete(id);
        return "redirect:/api/v1/doc/getAll";
    }
}
