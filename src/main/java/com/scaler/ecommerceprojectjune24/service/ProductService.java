package com.scaler.ecommerceprojectjune24.service;

import com.scaler.ecommerceprojectjune24.exception.CategoryNotFoundException;
import com.scaler.ecommerceprojectjune24.exception.ProductAlreadyExistsException;
import com.scaler.ecommerceprojectjune24.exception.ProductNotFoundException;
import com.scaler.ecommerceprojectjune24.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    public List<Product> getAllProducts();

    public Product getProductById(Integer id) throws ProductNotFoundException;

    public String[] getAllCategories();

    public List<Product> getInCategory(String category) throws CategoryNotFoundException;

    public Product deleteProduct(Integer id) throws ProductNotFoundException;

    public Product updateProduct(String title, String description, String image, Double price, Integer id) throws ProductNotFoundException;

    public Product createProduct(String title, String description, String image, Double price, String category) throws CategoryNotFoundException, ProductAlreadyExistsException;

    Page<Product> getPaginatedProducts(Integer pageSize, Integer pageNo);}
