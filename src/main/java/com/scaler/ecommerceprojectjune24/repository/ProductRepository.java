package com.scaler.ecommerceprojectjune24.repository;

import com.scaler.ecommerceprojectjune24.model.Category;
import com.scaler.ecommerceprojectjune24.model.Product;
import com.scaler.ecommerceprojectjune24.repository.projection.ProductWithTitleAndId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//import java.awt.print.Pageable;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    //Inserting a new Product into the Database
    Product save(Product p);

    //Get All Products
    List<Product> findAll();

    //Get Product by Id
    Product findProductById(Integer id);

    void deleteById(Integer id);

    Product findByTitle(String title);

    List<Product> findProductsByCategory(Category category);

    Page<Product> findAll(Pageable pageable);

    @Query("select p from Product p where p.title=:title and p.id=:id")
    Product getProductByTitleAndId(@Param("title") String title, @Param("id") Integer id);

    @Query("select p.title, p.id from Product p where p.price=:price")
    List<ProductWithTitleAndId> getTitleAndIdOfTheProductsByPrice(@Param("price") String price);


}
