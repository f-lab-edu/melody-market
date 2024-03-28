package com.melodymarket.presentation.theater.dto;

import com.melodymarket.application.theater.dto.TheaterDto;
import com.melodymarket.domain.theater.entity.Theater;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class TheaterResponseDto {
    Long id;
    String name;
    String location;

    public static TheaterResponseDto from(TheaterDto theaterDto) {
        return TheaterResponseDto.builder()
                .name(theaterDto.getName())
                .location(theaterDto.getLocation())
                .build();
    }

    public static TheaterResponseDto from(Theater theater) {
        return TheaterResponseDto.builder()
                .id(theater.getId())
                .name(theater.getName())
                .location(theater.getLocation())
                .build();
    }
}
