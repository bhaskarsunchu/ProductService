package com.scaler.ecommerceprojectjune24;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.scaler.ecommerceprojectjune24.model.Category;
import com.scaler.ecommerceprojectjune24.model.Product;
import com.scaler.ecommerceprojectjune24.repository.CategoryRepository;
import com.scaler.ecommerceprojectjune24.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ECommerceProjectJune24ApplicationTests {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Test
    void contextLoads() {
    }


}
