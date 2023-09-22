package com.microservice.orderservice.dto;

import com.microservice.orderservice.model.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {

    private Long id;

    private String skuName;

    private BigDecimal price;

    private Integer quantity;

    private Order orderId;
}
