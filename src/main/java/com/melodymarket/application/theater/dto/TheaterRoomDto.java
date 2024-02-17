package com.melodymarket.application.theater.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TheaterRoomDto {
    String roomName;
    List<TheaterSeatDto> seatData;
}
