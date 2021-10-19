package com.example.demoes.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @AllArgsConstructor @NoArgsConstructor
public class RequestEntity {
    private String id = "";
    private String name ="";
    private Double price = 0.0;
    private String category ="";
    private String location ="";
    private String description ="";

}
