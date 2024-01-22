package com.melodymarket.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melodymarket.application.dto.UserDto;
import com.melodymarket.application.service.UserJoinServiceImpl;
import com.melodymarket.presentation.admin.controller.JoinMemberController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("회원가입 중복 검사 테스트")
@WithMockUser(roles = "USER")// 유저 권한을 가지고 요청을 수행 하도록 함
@WebMvcTest(JoinMemberController.class) // 해당 컨트롤러만 테스트 가능하도록 함
class JoinMemberControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    JoinMemberController joinMemberController;
    @MockBean
    UserJoinServiceImpl userJoinServiceImpl;

    private final Validator validator = new LocalValidatorFactoryBean();


    @Test
    @DisplayName("[GET] 존재하지 않는 유저아이디 체크")
    void isUserIdAvailable() throws Exception {
        //given
        //when&then
        mockMvc.perform(get("/v1/member/join/check-userid?user-id=elephant"))
                .andExpect(status().isOk())
                .andExpect(content().string("사용 가능한 아이디 입니다."));
        // 한번만 수행이 되는지 체크
        verify(userJoinServiceImpl, times(1)).checkUserIdDuplication("elephant");
    }

    @Test
    @DisplayName("[GET] 존재하는 유저아이디 체크")
    void isUserIdNotAvailable() throws Exception {
        //given
        when(userJoinServiceImpl.checkUserIdDuplication("elephant")).thenReturn(true);

        //when & then
        mockMvc.perform(get("/v1/member/join/check-userid?user-id=elephant"))
                .andExpect(status().isOk())
                .andExpect(content().string("이미 사용중인 아이디 입니다."));

        // 한번만 수행이 되는지 체크
        verify(userJoinServiceImpl, times(1)).checkUserIdDuplication("elephant");
    }

    @Test
    @DisplayName("[GET] 존재하는 닉네임 체크")
    void isNicknameAvailable() throws Exception {
        //given
        //when&then
        mockMvc.perform(get("/v1/member/join/check-nickname?nickname=elephant"))
                .andExpect(status().isOk())
                .andExpect(content().string("사용 가능한 닉네임 입니다."));
        // 한번만 수행이 되는지 체크
        verify(userJoinServiceImpl, times(1)).checkNicknameDuplication("elephant");
    }

    @Test
    @DisplayName("[GET] 존재하는 닉네임 체크")
    void isNicknameNotAvailable() throws Exception {
        //given
        when(userJoinServiceImpl.checkNicknameDuplication("elephant")).thenReturn(true);

        //when & then
        mockMvc.perform(get("/v1/member/join/check-nickname?nickname=elephant"))
                .andExpect(status().isOk())
                .andExpect(content().string("이미 사용중인 닉네임 입니다."));

        // 한번만 수행이 되는지 체크
        verify(userJoinServiceImpl, times(1)).checkNicknameDuplication("elephant");
    }

    @Test
    @DisplayName("[POST] 존재하는 닉네임 체크")
    void givenTestUser_whenSaveUser_thenSuccess() throws Exception {
        //given
        UserDto testUser = new UserDto();
        setTestUser(testUser);
        //json 형식으로 convert
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonTestUser = objectMapper.writeValueAsString(testUser);


        //when & then
        mockMvc.perform(post("/v1/member/join/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonTestUser))
                .andExpect(status().isOk())
                .andExpect(content().string("유저 생성에 성공했습니다."));

        // 한번만 수행이 되는지 체크
        verify(userJoinServiceImpl, times(1)).signUpUser(testUser);
    }

    private void setTestUser(UserDto testUser) {
        testUser.setUserId("testuser");
        testUser.setUserPasswd("test123!");
        testUser.setNickname("imtest");
        testUser.setBirthDate("19970908");
        testUser.setEmail("test@example.com");
    }

}