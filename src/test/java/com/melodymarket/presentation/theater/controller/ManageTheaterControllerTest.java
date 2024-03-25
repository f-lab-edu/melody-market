package com.melodymarket.presentation.theater.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melodymarket.application.theater.dto.TheaterDto;
import com.melodymarket.application.theater.dto.TheaterRoomDto;
import com.melodymarket.application.theater.dto.TheaterSeatDto;
import com.melodymarket.application.theater.service.ManageTheaterServiceImpl;
import com.melodymarket.domain.user.entity.User;
import com.melodymarket.infrastructure.security.MelodyUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("공연장 API 테스트")
@WebMvcTest(ManageTheaterController.class)
class ManageTheaterControllerTest {

    @MockBean
    ManageTheaterServiceImpl manageTheaterService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ManageTheaterController manageTheaterController;
    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        User testUser = User.builder().loginId("testUser").userPassword("testPassword").build();
        MelodyUserDetails userDetails = new MelodyUserDetails(testUser);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .defaultRequest(post("/**").with(csrf()))
                .build();
    }

    @Test
    @DisplayName("[POST] 공연장 등록 API 테스트")
    void givenTestTheater_whenAddTheater_thenSuccess() throws Exception {
        //given
        TheaterDto testTheater = createTestTheater();
        //json 형식으로 convert
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(testTheater);

        //when
        ResultActions resultActions = mockMvc.perform(post("/v1/theater/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
        //then
        resultActions.andExpect(status().isOk());
    }

    private TheaterDto createTestTheater() {
        return TheaterDto.builder()
                .name("공연장")
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