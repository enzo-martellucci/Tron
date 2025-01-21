package com.uphf.tron.service;

import com.uphf.tron.entity.*;
import com.uphf.tron.game.constants.Difficulty;
import com.uphf.tron.repository.AIPlayerRepository;
import com.uphf.tron.repository.InventoryItemRepository;
import com.uphf.tron.repository.MotoRepository;
import com.uphf.tron.repository.SkinRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SelectionService
{
    private final SecurityService securityService;

    private final InventoryItemRepository inventoryItemRepository;
    private final MotoRepository motoRepository;
    private final SkinRepository skinRepository;
    private final AIPlayerRepository aiPlayerRepository;

    public SelectionService(SecurityService securityService, InventoryItemRepository inventoryItemRepository, MotoRepository motoRepository, SkinRepository skinRepository, AIPlayerRepository aiPlayerRepository)
    {
        this.securityService = securityService;
        this.inventoryItemRepository = inventoryItemRepository;
        this.motoRepository = motoRepository;
        this.skinRepository = skinRepository;
        this.aiPlayerRepository = aiPlayerRepository;
    }

    public String getPlayerName()
    {
        return this.securityService.getPlayer().getUsername();
    }

    @Transactional
    public NavigableMap<Moto, List<Skin>> getAllPlayerAsset()
    {
        Inventory inventory = this.securityService.getPlayer().getInventory();
        NavigableMap<Moto, List<Skin>> mapMotoSkin = new TreeMap<>(Comparator.comparingInt(Moto::getPrice));

        List<Moto> lstMoto = this.inventoryItemRepository.findDistinctMotoByInventory(inventory).stream()
                .map(InventoryItem::getMoto)
                .toList();

        for (Moto moto : lstMoto)
        {
            List<Skin> lstSkin = this.inventoryItemRepository.findDistinctSkinByInventoryAndMoto(inventory, moto).stream()
                    .map(InventoryItem::getSkin)
                    .toList();

            mapMotoSkin.put(moto, lstSkin);
        }

        return mapMotoSkin;
    }

    public AIPlayer getRandomAI(Difficulty difficulty)
    {
        List<AIPlayer> lstAI = this.aiPlayerRepository.findAllByDifficulty(difficulty);
        Collections.shuffle(lstAI);

        return lstAI.getFirst();
    }

    public Moto getRandomMoto()
    {
        List<Moto> lstMoto = this.motoRepository.findAll();
        Collections.shuffle(lstMoto);

        return lstMoto.getFirst();
    }

    public Skin getRandomSkin(Moto moto)
    {
        List<Skin> lstSkin = this.skinRepository.findAllByMoto(moto);
        Collections.shuffle(lstSkin);

        return lstSkin.getFirst();
    }
}
