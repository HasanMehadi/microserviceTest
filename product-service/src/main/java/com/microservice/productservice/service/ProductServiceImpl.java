package com.microservice.productservice.service;

import com.microservice.productservice.dto.ProductDto;
import com.microservice.productservice.mappers.ProductMapper;
import com.microservice.productservice.model.Product;
import com.microservice.productservice.repository.ProductRepository;
import com.microservice.prodectservice.utils.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;
    @Override
    public ResponseEntity<?> createProduct(ProductDto productDto) {

        try{
            Product product = productMapper.mapProductDto(productDto);
            productRepository.save(product);
            log.info("product is saved");
            return ResponseGenerator.generateResponse("Created", HttpStatus.CREATED);
        }catch (Exception ex){
            log.error("failed to save product");
            return ResponseGenerator.generateResponse("Failed to save product", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public ResponseEntity<?> getProduct(Long id) {

        Optional<Product> product = productRepository.findById(id);

        if(product.isEmpty()){
            return ResponseGenerator.generateResponse("Product not found", HttpStatus.NOT_FOUND);
        }

        ProductDto productDto = productMapper.mapProduct(product.get());
        return ResponseGenerator.generateResponse(productDto.toString(),HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<?> getProductList() {

        List<Product> productList = productRepository.findAll();

        if(productList.isEmpty()){
            return ResponseGenerator.generateResponse("Product not found", HttpStatus.NOT_FOUND);
        }

        List<ProductDto> productDtoList = productMapper.mapProductDtoList(productList);
        return ResponseGenerator.generateResponse(productDtoList.toString(),HttpStatus.FOUND);
    }
}
