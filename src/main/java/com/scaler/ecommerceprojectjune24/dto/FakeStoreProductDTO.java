package com.scaler.ecommerceprojectjune24.dto;


import com.scaler.ecommerceprojectjune24.model.Category;
import com.scaler.ecommerceprojectjune24.model.Product;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class FakeStoreProductDTO implements Serializable {
    private Integer id;
    private String title;
    private String price;
    private String category;
    private String description;
    private String image;

    public Product toProduct(){
        Product p = new Product();
        p.setTitle(title);
        p.setPrice(price);
        p.setDescription(description);
        p.setImageURL(image);

        Category c = new Category();
        c.setName(category);
        p.setCategory(c);

        return p;
    }
}
