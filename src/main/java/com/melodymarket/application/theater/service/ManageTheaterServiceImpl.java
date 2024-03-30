package com.melodymarket.application.theater.service;

import com.melodymarket.application.theater.dto.TheaterDto;
import com.melodymarket.common.mapper.ResponseMapper;
import com.melodymarket.domain.theater.entity.Theater;
import com.melodymarket.domain.theater.entity.TheaterRoom;
import com.melodymarket.infrastructure.exception.DataDuplicateKeyException;
import com.melodymarket.infrastructure.exception.DataNotFoundException;
import com.melodymarket.infrastructure.jpa.theater.repository.TheaterRepository;
import com.melodymarket.infrastructure.jpa.theater.repository.TheaterRoomRepository;
import com.melodymarket.presentation.theater.dto.TheaterResponseDto;
import com.melodymarket.presentation.theater.dto.TheaterRoomResponseDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@Slf4j
public class ManageTheaterServiceImpl implements ManageTheaterService {

    static final int PAGE_SIZE = 10;
    TheaterRepository theaterRepository;
    TheaterRoomRepository theaterRoomRepository;
    ResponseMapper responseMapper;

    @Transactional
    @Override
    public TheaterResponseDto saveTheater(TheaterDto theaterDto, Long userId) {
        if (theaterRepository.existsByName(theaterDto.getName())) {
            throw new DataDuplicateKeyException("이미 등록 된 공연장 이름입니다.");
        }
        Theater theater = Theater.from(theaterDto, userId);
        setTheaterPersistence(theater);
        theaterRepository.save(theater);
        return TheaterResponseDto.from(theaterDto);
    }

    private void setTheaterPersistence(Theater theater) {
        theater.associateTheaterWithRooms();
        theater.getRooms().forEach(TheaterRoom::associateRoomsWithSeats);
    }

    public List<TheaterResponseDto> getTheaterList(Long userId, int pageNo, String criteria) {
        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE, Sort.by(Sort.Direction.DESC, criteria));
        Page<Theater> theaterPage = theaterRepository.findTheatersByUserId(userId, pageable);

        if (theaterPage.isEmpty()) {
            throw new DataNotFoundException("유저가 등록한 공연이 없습니다.");
        }

        return responseMapper.toTheaterResponseDto(theaterPage);
    }

    @Override
    public List<TheaterRoomResponseDto> getTheaterRoomList(Long userId, Long theaterId, int pageNo, String criteria) {
        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE, Sort.by(Sort.Direction.DESC, criteria));
        Page<TheaterRoom> theaterRoomsPage = theaterRoomRepository.findTheaterRoomsByTheaterId(theaterId, pageable);
        if (theaterRoomsPage.isEmpty()) {
            throw new DataNotFoundException("공연에 등록된 Hall이 없습니다.");
        }

        return responseMapper.toTheaterRoomResponseDto(theaterRoomsPage);
    }

}
