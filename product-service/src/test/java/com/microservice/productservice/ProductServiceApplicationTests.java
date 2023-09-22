package com.microservice.productservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.productservice.dto.ProductDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("should save product into db")
    void shouldCreateProduct() throws Exception {

        ProductDto productDto = getProductDto();
        String productDtoString=  objectMapper.writeValueAsString(productDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productDtoString))
                .andExpect(status().isCreated());
    }

    private ProductDto getProductDto(){
        return ProductDto.builder()
                .name("iPhone13")
                .sku("i13")
                .price(new BigDecimal(12.3545))
                .description("worthless")
                .build();
    }

    @Test
    @DisplayName("should get product with id")
    void shouldGetProduct() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/get-product/").param("id","1"))
                .andExpect(status().isFound());
    }

}
