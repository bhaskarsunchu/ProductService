package com.scaler.ecommerceprojectjune24.service;

import com.scaler.ecommerceprojectjune24.exception.CategoryNotFoundException;
import com.scaler.ecommerceprojectjune24.exception.ProductAlreadyExistsException;
import com.scaler.ecommerceprojectjune24.exception.ProductNotFoundException;
import com.scaler.ecommerceprojectjune24.model.Category;
import com.scaler.ecommerceprojectjune24.model.Product;
import com.scaler.ecommerceprojectjune24.repository.CategoryRepository;
import com.scaler.ecommerceprojectjune24.repository.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Primary
public class SelfProductService implements ProductService{

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public SelfProductService(ProductRepository productRepository,
                              CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Integer id) {
        return productRepository.findProductById(id);
    }

    @Override
    public String[] getAllCategories() {
        List<Category> allCategories = categoryRepository.findAll();
        String[] allCatagoryNames = new String[allCategories.size()];
        for(int i=0; i<allCategories.size(); i++){
            allCatagoryNames[i] = allCategories.get(i).getName();
        }
        return allCatagoryNames;
    }

    @Override
    public List<Product> getInCategory(String categoryName) throws CategoryNotFoundException {
        Category category = categoryRepository.findByName(categoryName);
        if(category == null){
            throw new CategoryNotFoundException("The Category " + categoryName + "doesn't exists");
            //return new ArrayList<>();
        }
        return productRepository.findProductsByCategory(category);
    }

    @Transactional
    @Override
    public Product deleteProduct(Integer id) throws ProductNotFoundException {
        Product productToBeDeleted = productRepository.findProductById(id);
        if(productToBeDeleted == null){
            throw new ProductNotFoundException("Product doesn't exists");
        }
        productRepository.deleteById(id);
        return productToBeDeleted;
    }

    @Override
    public Product updateProduct(String title, String description, String image, Double price, Integer id) throws ProductNotFoundException {
        // Find existing product by id
        Product existingProduct = productRepository.findProductById(id);
        if (existingProduct == null) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }

        // Update fields
        existingProduct.setTitle(title);
        existingProduct.setDescription(description);
        existingProduct.setImageURL(image);
        existingProduct.setPrice(String.valueOf(price));

        // Save updated product (JPA will handle the update)
        return productRepository.save(existingProduct);
    }

    @Override
    public Product createProduct(String title, String description, String image, Double price, String category) throws ProductAlreadyExistsException {

        Product productToBeSaved = new Product();

        if(productRepository.findByTitle(title) != null){
             throw new ProductAlreadyExistsException("This Product was already saved in Database ");
        }

        Category categoryToBeSaved = categoryRepository.findByName(category);

        if(categoryToBeSaved == null){
            Category createCategory = new Category();
            createCategory.setName(category);
            categoryToBeSaved = categoryRepository.save(createCategory);
        }

        productToBeSaved.setTitle(title);
        productToBeSaved.setDescription(description);
        productToBeSaved.setImageURL(image);
        productToBeSaved.setPrice(String.valueOf(price));
        productToBeSaved.setCategory(categoryToBeSaved);

        Product savedProduct = productRepository.save(productToBeSaved);

        return savedProduct;
    }

    @Override
    public Page<Product> getPaginatedProducts(Integer pageSize, Integer pageNo) {
        // Creating a pageable object with two parameters
        Pageable pageable = PageRequest.of(pageSize, pageNo);
        /*  Creating a pageable object & Products are sorted in Ascending orders by their id's
        Pageable pageable = PageRequest.of(pageSize, pageNo, Sort.Direction.ASC);
            Creating a pageable object & Products are sorted in Descending orders by their id's
        Pageable pageable = PageRequest.of(pageSize, pageNo, Sort.Direction.DESC);
            Creating a pageable object & Products are sorted in Ascending orders by 'price' or any user defined parameter
        Pageable pageable = PageRequest.of(pageSize, pageNo, Sort.Direction.ASC, "price");
         */

        // Returns page with products
        Page<Product> product = productRepository.findAll(pageable);
        return product;
    }

}
