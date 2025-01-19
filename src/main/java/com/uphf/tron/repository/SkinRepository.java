package com.uphf.tron.repository;

import com.uphf.tron.entity.Skin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkinRepository extends JpaRepository<Skin, Long>
{
    Optional<Skin> findByName(String name);
}
