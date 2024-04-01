package com.melodymarket.common.mapper;

import com.melodymarket.domain.theater.entity.Theater;
import com.melodymarket.domain.theater.entity.TheaterRoom;
import com.melodymarket.domain.theater.entity.TheaterSeat;
import com.melodymarket.presentation.theater.dto.TheaterResponseDto;
import com.melodymarket.presentation.theater.dto.TheaterRoomResponseDto;
import com.melodymarket.presentation.theater.dto.TheaterSeatResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ResponseMapper {
    ResponseMapper INSTANCE = Mappers.getMapper(ResponseMapper.class);

    List<TheaterRoomResponseDto> toTheaterRoomResponseDto(Page<TheaterRoom> theaterRoom);

    List<TheaterResponseDto> toTheaterResponseDto(Page<Theater> theater);

    List<TheaterSeatResponseDto> toTheaterSeatResponseDto(Page<TheaterSeat> theaterSeats);
}
