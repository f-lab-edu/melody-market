package com.melodymarket.infrastructure.jpa.theater.repository;

import com.melodymarket.domain.theater.entity.TheaterSeat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TheaterSeatRepository extends JpaRepository<TheaterSeat, Long> {
    Page<TheaterSeat> findByTheaterRoomIdAndSeatFloorOrderBySeatRowAscSeatNumberAsc(Long roomId, Integer seatFloor, Pageable pageable);

}
