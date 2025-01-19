package com.uphf.tron.repository;

import com.uphf.tron.entity.AIPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AIPlayerRepository extends JpaRepository<AIPlayer, Long>
{
}
