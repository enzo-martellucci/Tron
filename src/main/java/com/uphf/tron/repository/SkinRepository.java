package com.uphf.tron.repository;

import com.uphf.tron.entity.Moto;
import com.uphf.tron.entity.Skin;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkinRepository extends JpaRepository<Skin, Long>
{
    List<Skin> findAllByMoto(Moto moto);
    List<Skin> findAllByMoto(Moto moto, Sort sort);
}
