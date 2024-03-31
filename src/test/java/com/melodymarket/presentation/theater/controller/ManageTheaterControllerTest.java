package com.melodymarket.presentation.theater.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melodymarket.application.theater.dto.TheaterDto;
import com.melodymarket.application.theater.dto.TheaterRoomDto;
import com.melodymarket.application.theater.dto.TheaterSeatDto;
import com.melodymarket.application.theater.service.ManageTheaterServiceImpl;
import com.melodymarket.domain.theater.entity.Theater;
import com.melodymarket.domain.user.entity.User;
import com.melodymarket.infrastructure.jpa.theater.repository.TheaterRepository;
import com.melodymarket.infrastructure.security.MelodyUserDetails;
import com.melodymarket.presentation.theater.dto.TheaterResponseDto;
import com.melodymarket.presentation.theater.dto.TheaterRoomResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("공연장 API 테스트")
@WebMvcTest(ManageTheaterController.class)
class ManageTheaterControllerTest {

    @MockBean
    ManageTheaterServiceImpl manageTheaterService;
    @MockBean
    TheaterRepository theaterRepository;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ManageTheaterController manageTheaterController;
    @Autowired
    WebApplicationContext webApplicationContext;

    Long userId;


    @BeforeEach
    void setUp() {
        User testUser = Mockito.spy(User.builder().loginId("testUser").build());
        when(testUser.getId()).thenReturn(1L);
        MelodyUserDetails userDetails = new MelodyUserDetails(testUser);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        this.userId = userDetails.getId();

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

    @Test
    @DisplayName("[GET] 공연장 리스트 조회 API 테스트")
    void givenTestUser_whenGetTheaterList_thenSuccess() throws Exception {
        //given
        Long userId = this.userId;
        Theater theater = Mockito.mock(Theater.class);
        when(theater.getId()).thenReturn(userId);
        when(theater.getName()).thenReturn("공연장");
        when(theater.getLocation()).thenReturn("위치");
        TheaterResponseDto theaterResponseDto = new TheaterResponseDto(userId, "공연장", "위치");
        when(manageTheaterService.getTheaterList(Mockito.anyLong(), Mockito.anyInt(), Mockito.anyString()))
                .thenReturn(List.of(theaterResponseDto));

        //when
        ResultActions resultActions = mockMvc.perform(get("/v1/theater/list"));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value("OK"))
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].name").value("공연장"))
                .andExpect(jsonPath("$.data[0].location").value("위치"))
                .andExpect(jsonPath("$.data[0].id").value(1L));
    }

    @Test
    @DisplayName("[GET] 공연홀 리스트 조회 API 테스트")
    void givenTheaterId_whenGetTheaterRoomList_thenSuccess() throws Exception {
        //given
        Long userId = this.userId;
        Long theaterId = 1L;
        Long theaterRoomId = 1L;
        String hallName = "A홀";
        int seatCount = 30;
        TheaterRoomResponseDto theaterRoomResponseDto = new TheaterRoomResponseDto(theaterRoomId, hallName, seatCount);
        when(manageTheaterService.getTheaterRoomList(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyInt(), Mockito.anyString()))
                .thenReturn(List.of(theaterRoomResponseDto));

        //when
        ResultActions resultActions = mockMvc.perform(get("/v1/theater/list/"+theaterId+"/rooms"));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value("OK"))
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].id").value(theaterRoomId))
                .andExpect(jsonPath("$.data[0].seatCount").value(seatCount));
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