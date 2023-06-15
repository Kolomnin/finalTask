package com.example.finaltask.model.dto;

import lombok.Data;

import javax.persistence.Lob;

@Data
public class AvatarDTO {
    private Long id;
    private String name;
    private String originalFileName;
    private String contentType;
    private Long size;
    private byte [] bytes;
}
