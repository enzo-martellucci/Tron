package com.uphf.tron.service;

import com.uphf.tron.entity.Inventory;
import com.uphf.tron.entity.InventoryItem;
import com.uphf.tron.entity.Moto;
import com.uphf.tron.entity.Skin;
import com.uphf.tron.repository.InventoryItemRepository;
import com.uphf.tron.repository.InventoryRepository;
import com.uphf.tron.repository.MotoRepository;
import com.uphf.tron.repository.SkinRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

@Service
public class ShopService
{
    private final SecurityService securityService;
    private final InventoryRepository inventoryRepository;
    private final MotoRepository motoRepository;
    private final SkinRepository skinRepository;
    private final InventoryItemRepository inventoryItemRepository;

    public ShopService(SecurityService securityService, InventoryRepository inventoryRepository, MotoRepository motoRepository, SkinRepository skinRepository, InventoryItemRepository inventoryItemRepository)
    {
        this.securityService = securityService;
        this.inventoryRepository = inventoryRepository;
        this.motoRepository = motoRepository;
        this.skinRepository = skinRepository;
        this.inventoryItemRepository = inventoryItemRepository;
    }

    public int getCoins()
    {
        return this.securityService.getPlayer().getInventory().getCoins();
    }

    @Transactional
    public SortedMap<Moto, Boolean> getAllMotoOwned()
    {
        SortedMap<Moto, Boolean> motoOwned = new TreeMap<>(Comparator.comparingInt(Moto::getPrice));
        for (Moto moto : this.motoRepository.findAll()) {
            motoOwned.put(moto, false);
        }

        Inventory inventory = this.securityService.getPlayer().getInventory();
        for (InventoryItem item : this.inventoryItemRepository.findDistinctMotoByInventory(inventory))
            motoOwned.put(item.getMoto(), true);

        return motoOwned;
    }

    public boolean ownMoto(Moto moto)
    {
        Inventory inventory = this.securityService.getPlayer().getInventory();
        return this.inventoryItemRepository.findFirstByInventoryAndMoto(inventory, moto).isPresent();
    }

    public boolean ownSkin(Skin skin)
    {
        Inventory inventory = this.securityService.getPlayer().getInventory();
        return this.inventoryItemRepository.findFirstByInventoryAndSkin(inventory, skin).isPresent();
    }

    public Skin prevSkin(Moto moto, Skin skin)
    {
        List<Skin> lstSkin = this.skinRepository.findAllByMoto(moto, Sort.by(Sort.Order.asc("price")));
        for (int i = 0; i < lstSkin.size(); i++)
            if (lstSkin.get(i).getId().equals(skin.getId()))
                return lstSkin.get(i == 0 ? lstSkin.size() - 1 : i - 1);

        return skin;
    }

    public Skin nextSkin(Moto moto, Skin skin)
    {
        List<Skin> lstSkin = this.skinRepository.findAllByMoto(moto, Sort.by(Sort.Order.asc("price")));
        for (int i = 0; i < lstSkin.size(); i++)
            if (lstSkin.get(i).getId().equals(skin.getId()))
                return lstSkin.get((i + 1) % lstSkin.size());

        return skin;
    }

    public void unlockMoto(Moto moto) throws IllegalArgumentException
    {
        Inventory inventory = this.securityService.getPlayer().getInventory();
        if (inventory.getCoins() < moto.getPrice())
            throw new IllegalArgumentException("Not enough money!");

        this.inventoryItemRepository.save(new InventoryItem(inventory, moto, moto.getDefaultSkin()));
        inventory.setCoins(inventory.getCoins() - moto.getPrice());
        this.inventoryRepository.save(inventory);
    }

    public void unlockSkin(Skin skin) throws IllegalArgumentException
    {
        Inventory inventory = this.securityService.getPlayer().getInventory();
        if (inventory.getCoins() < skin.getPrice())
            throw new IllegalArgumentException("Not enough money!");

        this.inventoryItemRepository.save(new InventoryItem(inventory, skin.getMoto(), skin));
        inventory.setCoins(inventory.getCoins() - skin.getPrice());
        this.inventoryRepository.save(inventory);
    }
}
