package com.uphf.tron.repository;

import com.uphf.tron.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, InventoryItemId>
{
    List<InventoryItem> findDistinctMotoByInventory(Inventory inventory);
    Optional<InventoryItem> findFirstByInventoryAndSkin(Inventory inventory, Skin skin);
    Optional<InventoryItem> findFirstByInventoryAndMoto(Inventory inventory, Moto moto);
}
