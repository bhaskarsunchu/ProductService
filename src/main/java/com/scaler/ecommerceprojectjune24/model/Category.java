package com.scaler.ecommerceprojectjune24.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Transactional
public class Category extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L; // Add this line
    private String name;
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Product> products;
}
