package com.microservice.orderservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.orderservice.dto.OrderDto;
import com.microservice.orderservice.dto.OrderItemDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class OrderServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("should save product into db")
    void shouldCreateProduct() throws Exception {

        OrderDto orderDto = getOrderDto();
        String orderDtoString=  objectMapper.writeValueAsString(orderDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/order/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderDtoString))
                .andExpect(status().isCreated());
    }

    private OrderDto getOrderDto(){
        List<OrderItemDto> orderItemDtoList = new ArrayList<>() ;
        orderItemDtoList.add(OrderItemDto.builder().skuName("Apple").price(new BigDecimal(12.8745)).quantity(5).build());
        return OrderDto.builder().name("Kasmiri Apple").orderItemDtoList(orderItemDtoList).build();
    }

    @Test
    @DisplayName("should get order with id")
    void shouldGetOrder() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/order/get-order/").param("id","1"))
                .andExpect(status().isFound());
    }

}
