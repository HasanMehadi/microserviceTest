package com.microservice.inventoryservice.controller;

import com.microservice.inventoryservice.dto.InventoryDto;
import com.microservice.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/create")
    public ResponseEntity<?> createInventory(@RequestBody InventoryDto inventoryDto){

        return inventoryService.createInventory(inventoryDto);
    }

    @GetMapping("/get-inventory/")
    public ResponseEntity<?> getInventory(@RequestParam("id") Long id){
        return inventoryService.getInventory(id);
    }

    @GetMapping("/get-inventory/sku-code")
    public ResponseEntity<?> getInventoryBySkuCode(@RequestParam("sku") String sku){
        return inventoryService.getInventoryBySku(sku);
    }

    @GetMapping("/get-all-inventory")
    public ResponseEntity<?> getInventoryList(){
        return inventoryService.getInventoryList();
    }

}
