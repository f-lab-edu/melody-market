package com.melodymarket.application.theater.service;

import com.melodymarket.application.theater.dto.TheaterDto;
import com.melodymarket.application.theater.dto.TheaterRoomDto;
import com.melodymarket.application.theater.dto.TheaterSeatDto;
import com.melodymarket.infrastructure.exception.DataDuplicateKeyException;
import com.melodymarket.infrastructure.jpa.theater.repository.TheaterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@DisplayName("공연장 관리 서비스 테스트")
class TheaterManageServiceImplTest {
    @Autowired
    ManageTheaterService manageTheaterService;
    @Autowired
    TheaterRepository theaterRepository;
    TheaterDto theaterDto;

    @BeforeEach
    void insert() {
        this.theaterDto = createTestTheater("테스트 공연장");
        manageTheaterService.saveTheater(theaterDto);
    }

    @Test
    @DisplayName("새로운 공연장 등록 정상 테스트")
    void givenTheaterInfo_whenAddTheaterInfo_thenSuccess() {
        //given
        TheaterDto theaterTestDto = createTestTheater("새로운공연장");

        //when & then
        assertDoesNotThrow(() -> manageTheaterService.saveTheater(theaterTestDto));
    }

    @Test
    @DisplayName("이미 존재하는 공연장 이름 등록 예외 발생 테스트")
    void givenExistTheaterName_whenAddTheaterInfo_thenThrowException() {
        //given
        TheaterDto theaterTestDto = theaterDto;

        //when
        Exception exception = assertThrows(Exception.class, () -> manageTheaterService.saveTheater(theaterTestDto));

        //then
        assertTrue(exception instanceof DataDuplicateKeyException);
    }

    private TheaterDto createTestTheater(String name) {
        return TheaterDto.builder()
                .name(name)
                .location("위치")
                .roomsInfo(createTestTheaterRooms())
                .build();
    }

    private List<TheaterRoomDto> createTestTheaterRooms() {
        List<TheaterRoomDto> roomDtoList = new ArrayList<>();
        roomDtoList.add(
                TheaterRoomDto.builder()
                        .roomName("test hall")
                        .seatData(createTestTheaterSeats())
                        .build()
        );
        return roomDtoList;
    }

    private List<TheaterSeatDto> createTestTheaterSeats() {
        List<TheaterSeatDto> seatDtoList = new ArrayList<>();
        seatDtoList.add(
                TheaterSeatDto.builder()
                        .seatFloor(1)
                        .seatRow(1)
                        .seatNumber(1)
                        .build()
        );
        return seatDtoList;
    }

}