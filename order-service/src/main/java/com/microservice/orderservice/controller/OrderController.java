package com.microservice.orderservice.controller;

import com.microservice.orderservice.dto.OrderDto;

import com.microservice.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderDto orderDto){

        return orderService.createOrder(orderDto);
    }

    @GetMapping("/get-order/")
    public ResponseEntity<?> getOrder(@RequestParam("id") Long id){
        return orderService.getOrder(id);
    }

    @GetMapping("/get-all-order")
    public ResponseEntity<?> getOrderList(){
        return orderService.getOrderList();
    }

}
