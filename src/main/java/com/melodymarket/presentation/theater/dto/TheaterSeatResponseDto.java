package com.melodymarket.presentation.theater.dto;

import com.melodymarket.domain.theater.entity.TheaterSeat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class TheaterSeatResponseDto {
    Long id;
    int seatFloor;
    String seatRow;
    int seatNumber;

    public static TheaterSeatResponseDto from(TheaterSeat theaterSeat) {
        return TheaterSeatResponseDto.builder()
                .id(theaterSeat.getId())
                .seatFloor(theaterSeat.getSeatFloor())
                .seatRow(theaterSeat.getSeatRow())
                .seatNumber(theaterSeat.getSeatNumber())
                .build();
    }
}

