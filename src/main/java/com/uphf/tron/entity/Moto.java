package com.uphf.tron.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "moto")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class Moto extends Asset
{
    @Column(nullable = false)
    private double speed;

    @OneToMany(mappedBy = "moto")
    private List<Skin> lstSkin;

    @OneToOne
    @JoinColumn(name = "id_default_skin", unique = true)
    private Skin defaultSkin;

    @OneToMany(mappedBy = "moto")
    private List<InventoryItem> lstInventoryItem;
}
