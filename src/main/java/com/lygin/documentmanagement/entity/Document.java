package com.lygin.documentmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String fullName;
    private String path;
    private Date createDate;
    private DocType type;
}
