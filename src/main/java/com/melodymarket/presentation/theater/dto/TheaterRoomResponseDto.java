package com.melodymarket.presentation.theater.dto;

import com.melodymarket.domain.theater.entity.TheaterRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class TheaterRoomResponseDto {
    Long id;
    String name;
    int seatCount;

    public static TheaterRoomResponseDto from(TheaterRoom theaterRoom) {
        return TheaterRoomResponseDto.builder()
                .id(theaterRoom.getId())
                .name(theaterRoom.getName())
                .seatCount(theaterRoom.getSeatCount())
                .build();
    }
}

