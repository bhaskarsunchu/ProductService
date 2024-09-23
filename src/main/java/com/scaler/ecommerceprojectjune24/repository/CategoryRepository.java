package com.scaler.ecommerceprojectjune24.repository;

import com.scaler.ecommerceprojectjune24.model.Category;
import com.scaler.ecommerceprojectjune24.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category findByName(String name);

    List<Category> findAll();

    Category save(Category category);
}
