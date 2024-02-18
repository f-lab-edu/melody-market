package com.melodymarket.presentation.theater.dto;

import com.melodymarket.application.theater.dto.TheaterDto;
import lombok.Builder;

@Builder
public class TheaterResponseDto {
    String name;
    String location;

    public static TheaterResponseDto from(TheaterDto theaterDto) {
        return TheaterResponseDto.builder()
                .name(theaterDto.getName())
                .location(theaterDto.getLocation())
                .build();
    }
}
