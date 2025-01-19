package com.uphf.tron.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class InventoryItemId implements Serializable
{
    private Long inventory;
    private Long moto;
    private Long skin;
}
