package com.melodymarket.application.theater.service;

import com.melodymarket.application.theater.dto.TheaterDto;
import com.melodymarket.presentation.theater.dto.TheaterResponseDto;

import java.util.List;

public interface ManageTheaterService {
    TheaterResponseDto saveTheater(TheaterDto theaterDto, Long userId);

    List<TheaterResponseDto> getTheaterList(Long userId);
}
