package com.microservice.orderservice.service;


import com.microservice.orderservice.dto.OrderItemDto;
import com.microservice.orderservice.model.OrderItem;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderItemService {

    OrderItemDto createOrderItem(OrderItemDto orderItemDto);

    OrderItemDto getOrderItem(Long id);

    OrderItemDto getOrderItemByOrderId(Long id);

    List<OrderItem> getOrderItemList();
}
