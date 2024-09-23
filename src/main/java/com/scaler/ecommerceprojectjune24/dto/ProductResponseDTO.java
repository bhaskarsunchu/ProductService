package com.scaler.ecommerceprojectjune24.dto;


import com.scaler.ecommerceprojectjune24.model.Category;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ProductResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L; // Add this line
    private Integer id;
    private String title;
    private String description;
    private String price;
    private String imageURL;
    private Category category;
}
