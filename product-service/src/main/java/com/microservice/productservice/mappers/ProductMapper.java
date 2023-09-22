package com.microservice.productservice.mappers;


import com.microservice.productservice.dto.ProductDto;
import com.microservice.productservice.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductMapper {
    ModelMapper modelMapper = new ModelMapper();

    public ProductDto mapProduct(Product product) {
        return modelMapper.map(product, ProductDto.class);
    }

    public Product mapProductDto(ProductDto ProductDto) {
        return modelMapper.map(ProductDto, Product.class);
    }

    public List<ProductDto> mapProductDtoList(List<Product> productList) {
        List<ProductDto> ProductDtoS = new ArrayList<>();
        for (Product product : productList) {
            ProductDtoS.add(modelMapper.map(product, ProductDto.class));
        }
        return ProductDtoS;
    }

    public List<Product> mapProductList(List<ProductDto> ProductDtos) {
        List<Product> products = new ArrayList<>();
        for (ProductDto ProductDto : ProductDtos) {
            products.add(modelMapper.map(ProductDto, Product.class));
        }
        return products;
    }

}
