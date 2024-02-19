package com.melodymarket.application.theater.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TheaterSeatDto {
    Integer seatFloor;
    Integer seatRow;
    Integer seatNumber;
}
