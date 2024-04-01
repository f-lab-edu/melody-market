package com.melodymarket.infrastructure.jpa.theater.repository;

import com.melodymarket.domain.theater.entity.TheaterRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheaterRoomRepository extends JpaRepository<TheaterRoom, Long> {
    Page<TheaterRoom> findTheaterRoomsByTheaterId(Long theaterId, Pageable pageable);
}
