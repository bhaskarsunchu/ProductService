package com.scaler.ecommerceprojectjune24.controller;


import com.scaler.ecommerceprojectjune24.dto.ErrorDTO;
import com.scaler.ecommerceprojectjune24.exception.CategoryNotFoundException;
import com.scaler.ecommerceprojectjune24.exception.ProductAlreadyExistsException;
import com.scaler.ecommerceprojectjune24.exception.ProductNotFoundException;
import com.scaler.ecommerceprojectjune24.service.SelfProductService;
import jakarta.annotation.Nullable;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.scaler.ecommerceprojectjune24.dto.CreateProductRequestDTO;
import com.scaler.ecommerceprojectjune24.dto.ProductResponseDTO;
import com.scaler.ecommerceprojectjune24.model.Product;
import com.scaler.ecommerceprojectjune24.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
public class ProductController {

    ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping("/products")
    public Product createProduct(@RequestBody CreateProductRequestDTO cprDTO) throws CategoryNotFoundException, ProductAlreadyExistsException {
        Product product = productService.createProduct(cprDTO.getTitle(), cprDTO.getDescription(),
                cprDTO.getImage(), cprDTO.getPrice(), cprDTO.getCategory());
        return product;
    }

    @PutMapping("/products/{id}")
    public ProductResponseDTO updateProduct(@PathVariable("id") Integer id, @RequestBody CreateProductRequestDTO cprDTO) throws ProductNotFoundException {
        Product product = productService.updateProduct(cprDTO.getTitle(), cprDTO.getDescription(),
                cprDTO.getImage(), cprDTO.getPrice(), id);
        return convertProductToResponseDTO(product);
    }

    //@Cacheable(value = "product")
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable("id") Integer id) throws ProductNotFoundException {
        Product product = productService.getProductById(id);
        if(product == null){
            throw new ProductNotFoundException("Some error regarding product");
            //return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

        // Conversion from Product to ProductResponseDTO and return
        ProductResponseDTO response = convertProductToResponseDTO(product);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/products")
//    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(@Nullable @RequestHeader("AUTH_TOKEN") String token,
//                                                                   @Nullable @RequestHeader("USER_ID") Long userId){
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(){
//        // Check whether the Token exists
//        if(token == null || userId == null){
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//
//        ValidateTokenResponseDTO response = authenticationClient.validate(token, userId);
//
//        // Check whether the Token is Valid or Not
//        if(response.getSessionStatus().equals(SessionStatus.INVALID)){
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//
//        // Check whether the User has Permission or Not
//        boolean isUserAdmin = false;
//        for(Role role : response.getUserDTO().getRoles()){
//            if(role.getName().equals("ADMIN")){
//                isUserAdmin = true;
//            }
//        }
//        if(!isUserAdmin){
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }

        List<Product> products = productService.getAllProducts();
        List<ProductResponseDTO> prDTO = new ArrayList<>();
        for(Product p : products){
            prDTO.add(convertProductToResponseDTO(p));
        }
        return new ResponseEntity<>(prDTO, HttpStatus.OK);
    }

    @GetMapping("/products/categories")
    public String[] getAllCategories(){
        String[] categories = productService.getAllCategories();
        //return Arrays.asList(categories);
        return categories;
    }

    @GetMapping("/products/category/{category}")
    public List<ProductResponseDTO> getInCategory(@PathVariable("category") String category) throws CategoryNotFoundException {
        List<Product> products = productService.getInCategory(category);
        List<ProductResponseDTO> prDTO = new ArrayList<>();
        for(Product p : products){
            prDTO.add(convertProductToResponseDTO(p));
        }
        return prDTO;
    }

    @DeleteMapping("/products/{id}")
    public ProductResponseDTO deleteProduct(@PathVariable("id") Integer id) throws ProductNotFoundException {
        Product product = productService.deleteProduct(id);
        return convertProductToResponseDTO(product);
    }

    @GetMapping("/products/{pageNo}/{pageSize}")
    public ResponseEntity<List<Product>> getPaginatedProducts(@PathVariable("pageNo") Integer pageNo,
                                                                   @PathVariable("pageSize") Integer pageSize){
        Page<Product> productPage = productService.getPaginatedProducts(pageNo, pageSize);
        System.out.println("Product page : " + productPage);
        return ResponseEntity.ok(productPage.getContent());
    }

    public ProductResponseDTO convertProductToResponseDTO(Product product){
        ProductResponseDTO prDTO = new ProductResponseDTO();
        prDTO.setId(product.getId());
        prDTO.setTitle(product.getTitle());
        prDTO.setDescription(product.getDescription());
        prDTO.setPrice(product.getPrice());
        prDTO.setImageURL(product.getImageURL());
        prDTO.setCategory(product.getCategory());
        return prDTO;

    }

}
