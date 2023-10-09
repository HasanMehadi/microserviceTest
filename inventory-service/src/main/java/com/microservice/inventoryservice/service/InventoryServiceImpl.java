package com.microservice.inventoryservice.service;


import com.microservice.inventoryservice.dto.InventoryDto;
import com.microservice.inventoryservice.mappers.ModelEntityConversionUtil;
import com.microservice.inventoryservice.model.Inventory;
import com.microservice.inventoryservice.repository.InventoryRepository;
import com.microservice.inventoryservice.utils.ResponseGenerator;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ModelEntityConversionUtil modelMapper;
    @Override
    public ResponseEntity<?> createInventory(InventoryDto inventoryDto) {

        try{
            Inventory inventory = modelMapper.convert(inventoryDto,Inventory.class);
            inventoryRepository.save(inventory);
            log.info("Inventory is saved");
            return ResponseGenerator.generateResponse("Created", HttpStatus.CREATED);
        }catch (Exception ex){
            log.error("failed to save Inventory");
            return ResponseGenerator.generateResponse("Failed to save Inventory", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public ResponseEntity<?> getInventory(Long id) {

        Optional<Inventory> inventory = inventoryRepository.findById(id);

        if(inventory.isEmpty()){
            return ResponseGenerator.generateResponse("Inventory not found", HttpStatus.NOT_FOUND);
        }

        InventoryDto inventoryDto = modelMapper.convert(inventory.get(),InventoryDto.class);
        return ResponseGenerator.generateResponse(inventoryDto.toString(),HttpStatus.FOUND);
    }

    @Override
    @Transactional(readOnly = true)
    @SneakyThrows
    public ResponseEntity<?> getInventoryBySku(String sku) {
        Optional<Inventory> inventory = inventoryRepository.findBySkuIgnoreCase(sku);

        if(inventory.isEmpty()){
            return ResponseGenerator.generateResponse("Inventory not found", HttpStatus.NOT_FOUND);
        }

        InventoryDto inventoryDto = modelMapper.convert(inventory.get(),InventoryDto.class);
        return ResponseGenerator.generateResponse(inventoryDto.toString(),HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<?> getInventoryList() {

        List<Inventory> inventoryList = inventoryRepository.findAll();

        if(inventoryList.isEmpty()){
            return ResponseGenerator.generateResponse("Inventory not found", HttpStatus.NOT_FOUND);
        }

        List<InventoryDto> inventoryDtoList = modelMapper.convertList(inventoryList,InventoryDto.class);
        return ResponseGenerator.generateResponse(inventoryDtoList.toString(),HttpStatus.FOUND);
    }
}
