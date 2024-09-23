package com.scaler.ecommerceprojectjune24.service;

import com.scaler.ecommerceprojectjune24.dto.CreateProductRequestDTO;
import com.scaler.ecommerceprojectjune24.dto.FakeStoreProductDTO;
import com.scaler.ecommerceprojectjune24.exception.ProductNotFoundException;
import com.scaler.ecommerceprojectjune24.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@Primary
@Service("fakeStoreService")
public class FakeStoreService implements ProductService{

    FakeStoreProductDTO fakeStoreProductDTO = new FakeStoreProductDTO();

    private RestTemplate restTemplate;

    public FakeStoreService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Product> getAllProducts() {
        FakeStoreProductDTO[] response = restTemplate.getForObject("https://fakestoreapi.com/products",
                FakeStoreProductDTO[].class);
        List<Product> products = new ArrayList<>();
        for(FakeStoreProductDTO dto : response){
            products.add(dto.toProduct());
        }
        return products;
    }

    @Override
    public Product getProductById(Integer id)  throws ProductNotFoundException {
        ResponseEntity<FakeStoreProductDTO> response = restTemplate.getForEntity("https://fakestoreapi.com/products/" + id,
                FakeStoreProductDTO.class);
        if(response.getStatusCode() != HttpStatus.OK){
            throw new ProductNotFoundException("Product not found for ID: " + id);
        }
        FakeStoreProductDTO frDTO = response.getBody();
        return frDTO.toProduct();
    }

    @Override
    public String[] getAllCategories() {
        String[] categories = restTemplate.getForObject("https://fakestoreapi.com/products/categories", String[].class);
        return categories;
    }

    @Override
    public List<Product> getInCategory(String category) {
        FakeStoreProductDTO[] response = restTemplate.getForObject("https://fakestoreapi.com/products/category/" + category,
                FakeStoreProductDTO[].class);
        List<Product> products = new ArrayList<>();
        for(FakeStoreProductDTO dto : response){
            products.add(dto.toProduct());
        }
        return products;
    }

    @Override
    public Product deleteProduct(Integer id) {
        ResponseEntity<FakeStoreProductDTO> response = restTemplate.getForEntity("https://fakestoreapi.com/products/" + id,
                FakeStoreProductDTO.class);
        FakeStoreProductDTO frDTO = response.getBody();
        return frDTO.toProduct();
    }

    @Override
    public Product updateProduct(String title, String description, String image, Double price, Integer id) {
        // Create request body
        CreateProductRequestDTO requestBody = new CreateProductRequestDTO();
        requestBody.setTitle(title);
        requestBody.setDescription(description);
        requestBody.setImage(image);
        requestBody.setPrice(price);

        // Set headers if needed (optional, but recommended)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // Assuming you're sending JSON

        // Create HttpEntity with body and headers
        HttpEntity<CreateProductRequestDTO> requestEntity = new HttpEntity<>(requestBody, headers);

        // Send PUT request to update product
        ResponseEntity<FakeStoreProductDTO> response = restTemplate.exchange(
                "https://fakestoreapi.com/products/" + id, // API endpoint
                HttpMethod.PUT,                           // HTTP method for updating
                requestEntity,                            // Request body and headers
                FakeStoreProductDTO.class                 // Response type
        );

        // Convert response to Product object and return
        if (response.getBody() != null) {
            return response.getBody().toProduct(); // Convert FakeStoreProductDTO to your Product model
        }

        return null; // Return null if no product was updated
    }

    @Override
    public Product createProduct(String title, String description, String image, Double price, String category) {
        CreateProductRequestDTO requestBody = new CreateProductRequestDTO();
        requestBody.setTitle(title);
        requestBody.setDescription(description);
        requestBody.setImage(image);
        requestBody.setPrice(price);
        requestBody.setCategory(category);
        FakeStoreProductDTO response = restTemplate.postForObject("https://fakestoreapi.com/products", requestBody,
                FakeStoreProductDTO.class);
        return response.toProduct();
    }

    @Override
    public Page<Product> getPaginatedProducts(Integer pageNo, Integer pageSize) {
        return null;
    }

}
