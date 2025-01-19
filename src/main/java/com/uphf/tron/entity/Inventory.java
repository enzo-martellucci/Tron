package com.uphf.tron.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "inventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Integer coins;

    @Column(nullable = false)
    private Double level;

    @OneToMany(mappedBy = "inventory")
    private List<InventoryItem> inventoryItems;

    @OneToOne(mappedBy = "inventory")
    private HumanPlayer player;
}