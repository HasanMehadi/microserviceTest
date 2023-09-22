package com.microservice.productservice.service;

import com.microservice.productservice.dto.ProductDto;
import com.microservice.productservice.model.Product;
import org.springframework.http.ResponseEntity;

public interface ProductService {

    ResponseEntity<?> createProduct(ProductDto productDto);

    ResponseEntity<?> getProduct(Long id);

    ResponseEntity<?> getProductList();
}
