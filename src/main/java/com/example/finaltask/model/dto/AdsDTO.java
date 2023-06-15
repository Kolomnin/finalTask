package com.example.finaltask.model.dto;

import com.example.finaltask.model.entity.User;
import lombok.Data;

@Data
public class AdsDTO {
    private User author;
    private String image;
    private Long id;
    private Integer price;
    private String title;

    private String description;
}

