package com.uphf.tron.repository;

import com.uphf.tron.entity.Inventory;
import com.uphf.tron.entity.InventoryItem;
import com.uphf.tron.entity.InventoryItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, InventoryItemId>
{
    List<InventoryItem> findDistinctMotoByInventory(Inventory inventory);
}
