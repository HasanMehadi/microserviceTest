package com.microservice.orderservice.apiGateway;

import com.microservice.orderservice.model.OrderItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class ApiManagerGateway {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public String getInventoryBySku(OrderItem orderItem){
        ResponseEntity entity = webClientBuilder.build().
                get().
                uri("http://INVENTORY-SERVICE/api/inventory/get-inventory/sku-code?sku="+orderItem.getSkuName()).
                retrieve().
                //bodyToMono(String.class).
                toEntity(String.class).
                block();

        return String.valueOf(entity.getBody());
    }
}
