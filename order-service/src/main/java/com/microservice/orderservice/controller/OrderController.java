package com.microservice.orderservice.controller;

import com.microservice.orderservice.dto.OrderDto;

import com.microservice.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallBackMethod")
    @TimeLimiter(name = "inventory", fallbackMethod = "fallBackMethod")
    @Retry(name = "inventory" , fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "inventory", fallbackMethod = "fallBackMethod")
    public CompletableFuture<String> createOrder(@RequestBody OrderDto orderDto){

        return CompletableFuture.supplyAsync(()->orderService.createOrder(orderDto));
    }

    @GetMapping("/get-order/")
    public ResponseEntity<?> getOrder(@RequestParam("id") Long id){
        return orderService.getOrder(id);
    }

    @GetMapping("/get-all-order")
    public ResponseEntity<?> getOrderList(){
        return orderService.getOrderList();
    }


    public CompletableFuture<String> fallBackMethod(OrderDto orderDto, RuntimeException runtimeException){
        return CompletableFuture.supplyAsync(()->"Ops!! For some internal issue order placement has been failed. Please Try after sometime.");
    }
}
