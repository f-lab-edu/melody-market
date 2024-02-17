package com.melodymarket.application.theater.service;

import com.melodymarket.application.theater.dto.TheaterDto;
import com.melodymarket.presentation.theater.dto.TheaterResponseDto;

public interface ManageTheaterService {
    TheaterResponseDto saveTheater(TheaterDto theaterDto);

    boolean findByTheaterName(String theaterName);

}
