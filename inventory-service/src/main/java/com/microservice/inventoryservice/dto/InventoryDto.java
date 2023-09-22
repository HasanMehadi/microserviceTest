package com.microservice.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDto {

    private String id;

    private String name;

    private String description;

    private String sku;

    private BigDecimal price;
}
