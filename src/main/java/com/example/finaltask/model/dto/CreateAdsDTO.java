package com.example.finaltask.model.dto;

import lombok.Data;

@Data
public class CreateAdsDTO {
    private String description;
    private Integer price;
    private String title;
    private Integer author;
    private String image;
    private Integer pk;

//    private String image;
}
