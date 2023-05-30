package com.example.finaltask.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private Integer author;
    private String authorImage;
    private String authorFirstName;
    private Long createdAt;
    private Integer pk;
    private String text;
}
