package com.uphf.tron.repository;

import com.uphf.tron.entity.Moto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MotoRepository extends JpaRepository<Moto, Long>
{
    @EntityGraph("lstSkin")
    Optional<Moto> findByName(String name);
}
