package com.example.finaltask.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseWrapperComment {
    private int count;
    private List<CommentDTO> results;
}