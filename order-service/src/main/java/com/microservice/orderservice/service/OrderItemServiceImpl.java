package com.microservice.orderservice.service;

import com.microservice.orderservice.dto.OrderItemDto;
import com.microservice.orderservice.model.OrderItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService{


    @Override
    public OrderItemDto createOrderItem(OrderItemDto orderItemDto) {
        return null;
    }

    @Override
    public OrderItemDto getOrderItem(Long id) {
        return null;
    }

    @Override
    public OrderItemDto getOrderItemByOrderId(Long id) {
        return null;
    }

    @Override
    public List<OrderItem> getOrderItemList() {
        return null;
    }
}
