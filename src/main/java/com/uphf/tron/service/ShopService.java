package com.uphf.tron.service;

import com.uphf.tron.entity.Moto;
import com.uphf.tron.repository.MotoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShopService
{
    private final MotoRepository motoRepository;

    public ShopService(MotoRepository motoRepository)
    {
        this.motoRepository = motoRepository;
    }

    @Transactional
    public List<Moto> getAllMoto()
    {
        System.out.println(this.motoRepository.findByName("Flynn").get().getLstSkin().get(0).getName());

        return null;
    }

//    public Map<Moto, Boolean> getAllMotoOwned()
//    {
//        SortedMap<Moto, Boolean> motoOwned = new TreeMap<>(Comparator.comparingInt(Moto::getPrice));
//        for (Moto moto : this.motoRepository.findAll())
//            motoOwned.put(moto, false);
//
//        HumanPlayer player = this.securityService.getPlayer();
//        for (InventoryItem item : this.inventoryItemRepository.findDistinctMotoByInventory(player.getInventory()))
//            motoOwned.put(item.getMoto(), true);
//
//        return motoOwned;
//    }
}
