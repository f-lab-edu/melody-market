package com.melodymarket.admin.controller;

import com.melodymarket.admin.service.UserJoinServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("회원가입 중복 검사 테스트")
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

}