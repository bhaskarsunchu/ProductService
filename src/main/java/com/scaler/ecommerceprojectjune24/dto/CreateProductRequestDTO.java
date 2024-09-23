package com.scaler.ecommerceprojectjune24.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class CreateProductRequestDTO {
    String title;
    Double price;
    String description;
    String image;
    String category;
}
