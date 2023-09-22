package com.microservice.inventoryservice.repository;



import com.microservice.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {

    Optional<Inventory> findById(Long aLong);
    Optional<Inventory> findBySkuIgnoreCase(String sku);
}
