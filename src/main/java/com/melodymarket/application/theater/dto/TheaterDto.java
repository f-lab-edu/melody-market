package com.melodymarket.application.theater.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TheaterDto {
    @NotBlank
    String name;
    @NotBlank
    String location;
    List<TheaterRoomDto> roomsInfo;

}
