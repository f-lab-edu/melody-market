package com.melodymarket.application.theater.service;

import com.melodymarket.application.theater.dto.TheaterDto;
import com.melodymarket.domain.theater.entity.TheaterEntity;
import com.melodymarket.infrastructure.exception.DataAccessCustomException;
import com.melodymarket.infrastructure.jpa.theater.repository.TheaterRepository;
import com.melodymarket.presentation.theater.dto.TheaterResponseDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
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
            throw new DataAccessCustomException("이미 등록 된 공연장 이름입니다.");
        }
        TheaterEntity theater = TheaterEntity.from(theaterDto);
        setTheaterPersistence(theater);
        try {
            theaterRepository.save(theater);
            return TheaterResponseDto.builder()
                    .name(theaterDto.getName())
                    .location(theaterDto.getLocation())
                    .build();
        } catch (DataAccessException e) {
            log.error("[saveTheater] 처리 중 오류가 발생했습니다={}", e.getMessage());
            throw new DataAccessCustomException("처리 중 오류가 발생했습니다");
        }

    }

    @Override
    public boolean findByTheaterName(String theaterName) {
        try {
            return theaterRepository.existsByName(theaterName);
        } catch (DataAccessException e) {
            log.error("[findByTheaterName] 처리 중 오류가 발생했습니다={}", e.getMessage());
            throw new DataAccessCustomException("처리 중 오류가 발생했습니다");
        }
    }

    private void setTheaterPersistence(TheaterEntity theater) {
        theater.getRooms().forEach(room -> {
            room.setTheater(theater);
            room.getSeats().forEach(seat -> seat.setTheaterRoom(room));
        });
    }
}
