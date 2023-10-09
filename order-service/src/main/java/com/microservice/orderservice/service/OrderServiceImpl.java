package com.microservice.orderservice.service;

import com.microservice.orderservice.apiGateway.ApiManagerGateway;
import com.microservice.orderservice.dto.OrderDto;
import com.microservice.orderservice.dto.OrderItemDto;
import com.microservice.orderservice.mappers.ModelEntityConversionUtil;
import com.microservice.orderservice.model.Order;
import com.microservice.orderservice.model.OrderItem;
import com.microservice.orderservice.repository.OrderItemRepository;
import com.microservice.orderservice.repository.OrderRepository;
import com.microservice.prodectservice.utils.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ModelEntityConversionUtil modelMapper;

    @Autowired
    private ApiManagerGateway apiManagerGateway;
    @Autowired
    private Tracer tracer;
    @Override
    public String createOrder(OrderDto orderDto) {


        if (orderDto.getOrderItemDtoList().isEmpty()) {
            return "Failed to save Order, No item found";
        }
        List<OrderItem> orderItemList = modelMapper.convertList(orderDto.getOrderItemDtoList(), OrderItem.class);
        Order order = modelMapper.convert(orderDto, Order.class);
        order = orderRepository.save(order);

        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrder(order);
            orderItemRepository.save(orderItem);
            Span inventorySpan = tracer.nextSpan().name("inventoryService");
            try(Tracer.SpanInScope spanInScope = tracer.withSpan(inventorySpan.start())){
                String inventory = apiManagerGateway.getInventoryBySku(orderItem);
            }finally {
                inventorySpan.end();
            }

        }
        log.info("Order is saved");
        return "Created";

    }

    @Override
    public ResponseEntity<?> getOrder(Long id) {

        Optional<Order> order = orderRepository.findById(id);

        if(order.isEmpty()){
            return ResponseGenerator.generateResponse("Order not found", HttpStatus.NOT_FOUND);
        }

        OrderDto orderDto = modelMapper.convert(order.get(), OrderDto.class);

        List<OrderItem> orderItemList = orderItemRepository.findByOrderId(orderDto.getId());

        orderDto.setOrderItemDtoList(modelMapper.convertList(orderItemList, OrderItemDto.class));
        return ResponseGenerator.generateResponse(orderDto.toString(),HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<?> getOrderList() {

        List<Order> orderList = orderRepository.findAll();

        if(orderList.isEmpty()){
            return ResponseGenerator.generateResponse("Order not found", HttpStatus.NOT_FOUND);
        }

        List<OrderDto> OrderDtoList = modelMapper.convertList(orderList, OrderDto.class);
        return ResponseGenerator.generateResponse(OrderDtoList.toString(),HttpStatus.FOUND);
    }
}
