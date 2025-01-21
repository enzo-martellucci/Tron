package com.uphf.tron.repository;

import com.uphf.tron.entity.AIPlayer;
import com.uphf.tron.game.constants.Difficulty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AIPlayerRepository extends JpaRepository<AIPlayer, Long>
{
    List<AIPlayer> findAllByDifficulty(Difficulty difficulty);
}
