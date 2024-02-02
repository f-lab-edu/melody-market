package com.melodymarket.presentation.admin.controller;

import com.melodymarket.application.dto.UserDto;
import com.melodymarket.application.service.UserInfoManageServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("회원조희 API 테스트")
@WithMockUser(roles = "USER")
@WebMvcTest(ManageMemberController.class)
class ManageMemberControllerTest {

    @MockBean
    UserInfoManageServiceImpl userInfoManageService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ManageMemberController manageMemberController;

    UserDto userDto = createTestUser();

    @Test
    @DisplayName("[GET] 유저 상세 정보 조회 API 테스트")
    void givenUserId_whenGetMappingWithUserIdFindSuccess_thenReturnUserInfo() throws Exception {
        //given
        Long userId = 1L;

        //when
        Mockito.when(userInfoManageService.getUserDetails(userId)).thenReturn(userDto);
        ResultActions resultActions = mockMvc.perform(get("/v1/member/details?user-id=" + userId));
        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.loginId").value("testuser"))
                .andExpect(jsonPath("$.username").value("테스트"))
                .andExpect(jsonPath("$.nickname").value("imtest"))
                .andExpect(jsonPath("$.birthDate").value("19970908"))
                .andExpect(jsonPath("$.email").value("test@example.com"));

    }

    private UserDto createTestUser() {
        UserDto userDto = new UserDto();
        userDto.setLoginId("testuser");
        userDto.setUsername("테스트");
        userDto.setNickname("imtest");
        userDto.setBirthDate("19970908");
        userDto.setEmail("test@example.com");

        return userDto;
    }


}