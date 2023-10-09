package com.microservice.productservice.controller;

import com.microservice.productservice.dto.ProductDto;
import com.microservice.productservice.model.Product;
import com.microservice.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductDto productDto){

        return productService.createProduct(productDto);
    }

    @GetMapping("/get-product/")
    public ResponseEntity<?> getProduct(@RequestParam("id") Long id){

        return productService.getProduct(id);
    }

    @GetMapping("/get-all-product")
    public ResponseEntity<?> getProductList(){
        return productService.getProductList();
    }

}
