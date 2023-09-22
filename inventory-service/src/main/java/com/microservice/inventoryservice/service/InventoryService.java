package com.microservice.inventoryservice.service;


import com.microservice.inventoryservice.dto.InventoryDto;
import org.springframework.http.ResponseEntity;

public interface InventoryService {

    ResponseEntity<?> createInventory(InventoryDto inventoryDto);

    ResponseEntity<?> getInventory(Long id);

    ResponseEntity<?> getInventoryBySku(String sku);

    ResponseEntity<?> getInventoryList();
}
