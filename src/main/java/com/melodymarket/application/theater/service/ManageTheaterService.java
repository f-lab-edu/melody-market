package com.melodymarket.application.theater.service;

import com.melodymarket.application.theater.dto.TheaterDto;
import com.melodymarket.presentation.theater.dto.TheaterResponseDto;
import com.melodymarket.presentation.theater.dto.TheaterRoomResponseDto;
import com.melodymarket.presentation.theater.dto.TheaterSeatResponseDto;

import java.util.List;

public interface ManageTheaterService {
    TheaterResponseDto saveTheater(TheaterDto theaterDto, Long userId);

    List<TheaterResponseDto> getTheaterList(Long userId, int pageNo, String criteria);

    List<TheaterRoomResponseDto> getTheaterRoomList(Long userId, Long theaterId, int pageNo, String criteria);

    List<TheaterSeatResponseDto> getTheaterSeatList(Long userId, Long theaterId, Long roomId, int floor, int pageNo, String criteria);
}
