package com.scaler.ecommerceprojectjune24.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDTO {
    private String name;

    public CategoryDTO(String name){
        this.name = name;
    }
}
