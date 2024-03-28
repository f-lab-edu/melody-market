package com.melodymarket.common.mapper;

import com.melodymarket.domain.theater.entity.Theater;
import com.melodymarket.presentation.theater.dto.TheaterResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ResponseMapper {
    ResponseMapper INSTANCE = Mappers.getMapper(ResponseMapper.class);

    //    TheaterRoomResponseDto toTheaterRoomResponseDto(TheaterRoom theaterRoom);
    List<TheaterResponseDto> toTheaterResponseDto(Page<Theater> theater);
}
