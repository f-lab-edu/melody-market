package com.melodymarket.presentation.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melodymarket.application.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("회원가입 중복 검사 테스트")
@WithMockUser(roles = "USER")
@WebMvcTest(JoinMemberController.class)
class JoinMemberControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    JoinMemberController joinMemberController;

    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .defaultRequest(post("/**").with(csrf()))
                .build();
    }


    @Test
    @DisplayName("[GET] 유저아이디 중복 검사 API")
    void isUserIdNotAvailable() throws Exception {
        // when
        mockMvc.perform(get("/v1/member/join/check-user-id?user-id=testuser"))
        // then
                .andExpect(status().isOk())
                .andExpect(content().string("사용 가능한 아이디 입니다."));
    }

    @Test
    @DisplayName("[GET] 닉네임 중복 검사 API 테스트")
    void isNicknameAvailable() throws Exception {

        //when
        mockMvc.perform(get("/v1/member/join/check-nickname?nickname=testnickname"))
        //then
                .andExpect(status().isOk())
                .andExpect(content().string("사용 가능한 닉네임 입니다."));
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("[POST] 회원 가입 API 테스트")
    void givenTestUser_whenSaveUser_thenSuccess() throws Exception {
        //given
        UserDto testUser = createTestUser();
        //json 형식으로 convert
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonTestUser = objectMapper.writeValueAsString(testUser);
        //when
        mockMvc.perform(post("/v1/member/join/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonTestUser))
        //then
                .andExpect(status().isOk())
                .andExpect(content().string("유저 생성에 성공했습니다."));
    }

    private UserDto createTestUser() {
        UserDto userDto = new UserDto();
        userDto.setUserId("testuser");
        userDto.setUsername("테스트");
        userDto.setUserPasswd("test123!");
        userDto.setNickname("imtest");
        userDto.setBirthDate("19970908");
        userDto.setEmail("test@example.com");

        return userDto;
    }


}