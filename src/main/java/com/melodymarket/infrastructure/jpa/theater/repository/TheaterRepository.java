package com.melodymarket.infrastructure.jpa.theater.repository;

import com.melodymarket.domain.theater.entity.Theater;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheaterRepository extends JpaRepository<Theater, Long> {
    @Override
    <S extends Theater> S save(S theaterEntity);

    boolean existsByName(String name);

    Page<Theater> findTheatersByUserId(Long userId, Pageable pageable);
}
