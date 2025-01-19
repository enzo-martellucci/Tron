package com.uphf.tron.repository;

import com.uphf.tron.entity.HumanPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HumanPlayerRepository extends JpaRepository<HumanPlayer, Long>
{
    Optional<HumanPlayer> findByUsername(String username);
}
