package com.uphf.tron.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.uphf.tron.entity.HumanPlayer;
import com.uphf.tron.entity.Inventory;
import com.uphf.tron.entity.InventoryItem;
import com.uphf.tron.entity.Moto;
import com.uphf.tron.repository.HumanPlayerRepository;
import com.uphf.tron.repository.InventoryItemRepository;
import com.uphf.tron.repository.InventoryRepository;
import com.uphf.tron.repository.MotoRepository;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityService
{
    private static final int BCRYPT_WORK_FACTOR = 12;

    private final HumanPlayerRepository playerRepository;
    private final InventoryRepository inventoryRepository;
    private final InventoryItemRepository inventoryItemRepository;
    private final MotoRepository motoRepository;

    @Getter
    private HumanPlayer player;

    public SecurityService(HumanPlayerRepository playerRepository, InventoryRepository inventoryRepository, InventoryItemRepository inventoryItemRepository, MotoRepository motoRepository)
    {
        this.playerRepository = playerRepository;
        this.inventoryRepository = inventoryRepository;
        this.inventoryItemRepository = inventoryItemRepository;
        this.motoRepository = motoRepository;
    }

    public void register(String username, String password, String confirmation) throws IllegalArgumentException
    {
        // Check errors
        if (username.length() < 3)
            throw new IllegalArgumentException("Username should be at least 3 characters long.");
        if (this.playerRepository.findByUsername(username).isPresent())
            throw new IllegalArgumentException("This username is already taken.");
        if (!password.equals(confirmation))
            throw new IllegalArgumentException("Password and password confirmation don't match.");
        if (password.length() < 6)
            throw new IllegalArgumentException("Password should be at least 6 characters long.");

        // Create the inventory
        Inventory inventory = Inventory.builder().coins(0).level(1.0).build();

        // Add the basic moto (Flynn - Basic)
        Optional<Moto> optionalMoto = this.motoRepository.findByName("Flynn");
        if (optionalMoto.isEmpty())
            throw new RuntimeException("Moto (Flynn) not found.");
        Moto moto = optionalMoto.get();

        InventoryItem inventoryItem = InventoryItem.builder().inventory(inventory).moto(moto).skin(moto.getDefaultSkin()).build();

        // Create the player
        String hashedPassword = BCrypt.withDefaults().hashToString(BCRYPT_WORK_FACTOR, password.toCharArray());
        this.player = HumanPlayer.builder().username(username).password(hashedPassword).inventory(inventory).build();

        // Persist the data
        this.inventoryRepository.save(inventory);
        this.inventoryItemRepository.save(inventoryItem);
        this.playerRepository.save(this.player);
    }

    public void login(String username, String password) throws IllegalArgumentException
    {
        Optional<HumanPlayer> optionalPlayer = this.playerRepository.findByUsername(username);
        if (optionalPlayer.isEmpty())
            throw new IllegalArgumentException("Invalid credentials.");
        HumanPlayer player = optionalPlayer.get();

        BCrypt.Result passwordCheck = BCrypt.verifyer().verify(password.toCharArray(), player.getPassword());
        if (!passwordCheck.verified)
            throw new IllegalArgumentException("Invalid credentials.");

        this.player = player;
    }

    public void logout()
    {
        this.player = null;
    }
}
