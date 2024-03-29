package com.melodymarket.presentation.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melodymarket.application.user.dto.UserDto;
import com.melodymarket.application.user.service.UserJoinServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("회원가입 중복 검사 테스트")
@WithMockUser(roles = "USER")
@WebMvcTest(JoinUserController.class)
class JoinUserControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    JoinUserController joinUserController;

    @MockBean
    UserJoinServiceImpl userJoinService;


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
        //given
        String testUser = "testuser";

        // when
        ResultActions resultActions = mockMvc.perform(get("/v1/user/join/check-login-id?login-id=" + testUser));

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("[GET] 닉네임 중복 검사 API 테스트")
    void isNicknameAvailable() throws Exception {
        //given
        String testNickname = "testnickname";

        //when
        ResultActions resultActions = mockMvc.perform(get("/v1/user/join/check-nickname?nickname=" + testNickname));

        //then
        resultActions.andExpect(status().isOk());
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
        ResultActions resultActions = mockMvc.perform(post("/v1/user/join/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTestUser));

        //then
        resultActions.andExpect(status().isOk());
    }

    public UserDto createTestUser() {
        return UserDto.builder()
                .loginId("testuser")
                .username("테스트")
                .userPassword("test123!")
                .nickname("imtest")
                .email("test@example.com")
                .birthDate("19970908")
                .build();
    }

}