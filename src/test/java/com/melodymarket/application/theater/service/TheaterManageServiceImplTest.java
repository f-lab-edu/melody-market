package com.melodymarket.application.theater.service;

import com.melodymarket.application.theater.dto.TheaterDto;
import com.melodymarket.application.theater.dto.TheaterRoomDto;
import com.melodymarket.application.theater.dto.TheaterSeatDto;
import com.melodymarket.common.mapper.ResponseMapper;
import com.melodymarket.common.mapper.ResponseMapperImpl;
import com.melodymarket.infrastructure.exception.DataDuplicateKeyException;
import com.melodymarket.infrastructure.exception.DataNotFoundException;
import com.melodymarket.infrastructure.jpa.theater.repository.TheaterRepository;
import com.melodymarket.presentation.theater.dto.TheaterResponseDto;
import com.melodymarket.presentation.theater.dto.TheaterRoomResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({ManageTheaterServiceImpl.class, ResponseMapperImpl.class})
@DisplayName("공연장 관리 서비스 테스트")
class TheaterManageServiceImplTest {
    @Autowired
    ManageTheaterService manageTheaterService;
    @Autowired
    TheaterRepository theaterRepository;
    @Autowired
    ResponseMapper responseMapper;
    TheaterDto theaterDto;
    final int insertTheaterRoom = 3;
    final int insertSeat = 5;
    final Long userId = 1L;

    @BeforeEach
    void insert() {
        this.theaterDto = createTestTheater("테스트 공연장");
        Long userId = 1L;
        manageTheaterService.saveTheater(theaterDto, userId);
    }

    @Test
    @Transactional
    @DisplayName("새로운 공연장 등록 정상 테스트")
    void givenTheaterInfo_whenAddTheaterInfo_thenSuccess() {
        //given
        TheaterDto theaterTestDto = createTestTheater("새로운공연장");
        Long userId = this.userId;

        //when & then
        assertDoesNotThrow(() -> manageTheaterService.saveTheater(theaterTestDto, userId));
    }

    @Test
    @DisplayName("이미 존재하는 공연장 이름 등록 예외 발생 테스트")
    void givenExistTheaterName_whenAddTheaterInfo_thenThrowException() {
        //given
        TheaterDto theaterTestDto = theaterDto;
        Long userId = this.userId;

        //when
        Exception exception = assertThrows(Exception.class, () -> manageTheaterService.saveTheater(theaterTestDto, userId));

        //then
        assertTrue(exception instanceof DataDuplicateKeyException);
    }

    @Test
    @DisplayName("공연장을 등록한 유저의 공연장 리스트 조회 시 성공")
    void givenRegisterTheaterByUser_whenGetTheaterList_thenGetTheaterListSuccessfully() {
        //given
        Long userId = this.userId;

        //when
        List<TheaterResponseDto> theaterResponseDtoList = manageTheaterService.getTheaterList(userId, 0, "name");

        //then
        Assertions.assertThat(theaterResponseDtoList).hasSize(1);
        Assertions.assertThat(theaterResponseDtoList.get(0).getName()).isEqualTo("테스트 공연장");
    }

    @Test
    @DisplayName("공연장을 등록하지 않은 유저의 공연장 리스트 조회 시 예외 발생 테스트")
    void givenNotRegisterTheaterByUser_whenGetTheaterList_thenThrowException() {
        //given
        Long userId = this.userId + 1L;

        //when
        Exception exception = assertThrows(Exception.class, () -> manageTheaterService.getTheaterList(userId, 0, "name"));

        //then
        assertTrue(exception instanceof DataNotFoundException);
    }

    @Test
    @DisplayName("공연홀이 등록되어 있는 공연장 홀 리스트 조회 시 성공")
    void givenRegisterTheaterRoomByTheaterId_whenGetTheaterRoomList_thenGetTheaterRoomListSuccessfully() {
        //given
        Long userId = this.userId;
        List<TheaterResponseDto> theaterResponseDtoList = manageTheaterService.getTheaterList(userId, 0, "name");
        Long theaterId = theaterResponseDtoList.get(0).getId();

        //when
        List<TheaterRoomResponseDto> theaterRoomList = manageTheaterService.getTheaterRoomList(userId, theaterId, 0, "name");

        //then
        Assertions.assertThat(theaterRoomList).hasSize(insertTheaterRoom);
        Assertions.assertThat(theaterRoomList.get(0).getName()).isEqualTo("test hall2"); // name으로 페이지네이션 했으므로 가장먼저 높은 숫자 출력
        Assertions.assertThat(theaterRoomList.get(2).getSeatCount()).isEqualTo(insertSeat);
    }

    @Test
    @DisplayName("존재하지 않는 공연장의 공연홀 리스트 조회 시 예외 발생 테스트")
    void givenNotRegisterTheaterRoomByTheaterId_whenGetTheaterRoomList_thenThrowException() {
        //given
        Long userId = this.userId;
        Long theaterId = -1L;

        //when
        Exception exception = assertThrows(Exception.class, () -> manageTheaterService.getTheaterRoomList(userId, theaterId, 0, "name"));

        //then
        assertTrue(exception instanceof DataNotFoundException);
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
        for (int i = 0; i < insertTheaterRoom; i++) {
            roomDtoList.add(TheaterRoomDto.builder().roomName("test hall" + i).seatData(createTestTheaterSeats()).build());
        }

        return roomDtoList;
    }

    private List<TheaterSeatDto> createTestTheaterSeats() {
        List<TheaterSeatDto> seatDtoList = new ArrayList<>();
        for (int i = 0; i < insertSeat; i++) {
            seatDtoList.add(TheaterSeatDto.builder().seatFloor(i).seatRow(5).seatNumber(1).build());
        }

        return seatDtoList;
    }

}