package com.melodymarket.application.theater.service;

import com.melodymarket.application.theater.dto.TheaterDto;
import com.melodymarket.domain.theater.entity.Theater;
import com.melodymarket.domain.theater.entity.TheaterRoom;
import com.melodymarket.infrastructure.exception.DataDuplicateKeyException;
import com.melodymarket.infrastructure.jpa.theater.repository.TheaterRepository;
import com.melodymarket.presentation.theater.dto.TheaterResponseDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@Slf4j
public class ManageTheaterServiceImpl implements ManageTheaterService {

    TheaterRepository theaterRepository;

    @Override
    public TheaterResponseDto saveTheater(TheaterDto theaterDto) {
        if (findByTheaterName(theaterDto.getName())) {
            throw new DataDuplicateKeyException("이미 등록 된 공연장 이름입니다.");
        }
        Theater theater = Theater.from(theaterDto);
        setTheaterPersistence(theater);
        theaterRepository.save(theater);
        return TheaterResponseDto.from(theaterDto);
    }

    @Override
    public boolean findByTheaterName(String theaterName) {
        return theaterRepository.existsByName(theaterName);
    }

    private void setTheaterPersistence(Theater theater) {
        theater.associateTheaterWithRooms();
        theater.getRooms().forEach(TheaterRoom::associateRoomsWithSeats);

    }
}
