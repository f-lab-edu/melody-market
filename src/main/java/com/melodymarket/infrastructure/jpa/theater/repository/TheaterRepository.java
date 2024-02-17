package com.melodymarket.infrastructure.jpa.theater.repository;

import com.melodymarket.domain.theater.entity.TheaterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheaterRepository extends JpaRepository<TheaterEntity, Long> {
    @Override
    <S extends TheaterEntity> S save(S theaterEntity);

    boolean existsByName(String name);
}
