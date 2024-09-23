package com.scaler.ecommerceprojectjune24.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Transactional
public class Product extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L; // Add this line
    private String title;
    private String description;
    private String price;
    private String imageURL;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;

}
