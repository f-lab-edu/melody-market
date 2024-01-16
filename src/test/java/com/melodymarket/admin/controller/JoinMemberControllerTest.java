package com.melodymarket.admin.controller;

import com.melodymarket.admin.service.UserJoinService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("회원가입 테스트")
@WebMvcTest(JoinMemberController.class) // 해당 컨트롤러만 테스트 가능하도록 함
class JoinMemberControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired JoinMemberController joinMemberController;
    @MockBean UserJoinService userJoinService;


    @Test
    @DisplayName("[GET] 존재하지 않는 유저아이디 체크")
    void isUserIdAvailable() throws Exception {
        //given
        //when&then
        mockMvc.perform(get("/v1/member/join/elephant/exists"))
                .andExpect(status().isOk())
                .andExpect(content().string("User not exists"));
        // 한번만 수행이 되는지 체크
        verify(userJoinService, times(1)).checkUserIdDuplication("elephant");
    }

    @Test
    @DisplayName("[GET] 존재하는 유저아이디 체크")
    void isUserIdNotAvailable() throws Exception {
        //given
        when(userJoinService.checkUserIdDuplication("elephant")).thenReturn(true);

        //when & then
        mockMvc.perform(get("/v1/member/join/elephant/exists"))
                .andExpect(status().isOk())
                .andExpect(content().string("User exists"));

        // 한번만 수행이 되는지 체크
        verify(userJoinService, times(1)).checkUserIdDuplication("elephant");
    }
}