package com.uphf.tron.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "skin")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class Skin extends Asset
{
    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private byte[] rawImage;

    @ManyToOne
    @JoinColumn(name = "id_moto", nullable = false)
    private Moto moto;

    @OneToMany(mappedBy = "skin")
    private List<InventoryItem> lstInventoryItem;
}
