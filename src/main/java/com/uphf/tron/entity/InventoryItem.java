package com.uphf.tron.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inventory_item")
@IdClass(InventoryItemId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryItem {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_inventory")
    private Inventory inventory;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_moto")
    private Moto moto;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_skin")
    private Skin skin;
}
