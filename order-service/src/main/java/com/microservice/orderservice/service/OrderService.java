package com.microservice.orderservice.service;

import com.microservice.orderservice.dto.OrderDto;
import org.springframework.http.ResponseEntity;

public interface OrderService {

    String createOrder(OrderDto orderDto);

    ResponseEntity<?> getOrder(Long id);

    ResponseEntity<?> getOrderList();
}
