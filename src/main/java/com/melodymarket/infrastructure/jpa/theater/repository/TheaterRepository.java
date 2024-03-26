package com.melodymarket.infrastructure.jpa.theater.repository;

import com.melodymarket.domain.theater.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TheaterRepository extends JpaRepository<Theater, Long> {
    @Override
    <S extends Theater> S save(S theaterEntity);

    boolean existsByName(String name);

    List<Theater> findTheatersByUserId(Long userId);
}
