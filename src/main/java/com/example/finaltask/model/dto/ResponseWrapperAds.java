package com.example.finaltask.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseWrapperAds<A> {

    private int count;
    private List<AdsDTO> results;
}