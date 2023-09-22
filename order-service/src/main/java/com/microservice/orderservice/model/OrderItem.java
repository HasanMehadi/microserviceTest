package com.microservice.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name="t_order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String skuName;

    private BigDecimal price;

    private Integer quantity;

    @JoinColumn(name = "order_id", nullable = true, referencedColumnName = "id")
    @ManyToOne
    private Order order;
}
