package com.melodymarket.presentation.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melodymarket.application.dto.UpdatePasswordDto;
import com.melodymarket.application.dto.UpdateUserDto;
import com.melodymarket.application.dto.UserDto;
import com.melodymarket.application.service.UserInfoManageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @Autowired
    WebApplicationContext webApplicationContext;


    UserDto userDto = createTestUser();

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .defaultRequest(post("/**").with(csrf()))
                .build();
    }

    @Test
    @DisplayName("[GET] 유저 상세 정보 조회 API 테스트")
    void givenUserId_whenGetMappingWithUserIdFindSuccess_thenReturnUserInfo() throws Exception {
        //given
        Long id = 1L;

        //when
        Mockito.when(userInfoManageService.getUserDetails(id)).thenReturn(userDto);
        ResultActions resultActions = mockMvc.perform(get("/v1/member/details/" + id));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.loginId").value("testuser"))
                .andExpect(jsonPath("$.username").value("테스트"))
                .andExpect(jsonPath("$.nickname").value("imtest"))
                .andExpect(jsonPath("$.birthDate").value("19970908"))
                .andExpect(jsonPath("$.email").value("test@example.com"));

    }

    @Test
    @DisplayName("[POST] 유저 비밀번호 변경 API 테스트")
    void givenUserIdAndOldUpdatePasswordDto_whenPostMapping_thenReturnSuccess() throws Exception {
        //given
        Long id = 1L;
        UpdatePasswordDto updatePasswordDto = getTestUpdatePasswordDto();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonTestDto = objectMapper.writeValueAsString(updatePasswordDto);

        //when
        ResultActions resultActions = mockMvc.perform(post("/v1/member/details/" + id + "/update-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTestDto));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string("비밀번호가 변경되었습니다."));
    }

    @Test
    @DisplayName("[POST] 유저 닉네임 변경 API 테스트")
    void givenUserIdAndNewNickname_whenPostMapping_thenReturnSuccess() throws Exception {
        //given
        Long id = 1L;
        UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setNickname("새로운닉네임");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonTestDto = objectMapper.writeValueAsString(updateUserDto);

        //when
        ResultActions resultActions = mockMvc.perform(post("/v1/member/details/" + id + "/update-user-info")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTestDto));

        //then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("[POST] 유저 삭제 테스트")
    void givenUserIdAndPassword_whenPostMapping_thenReturn3xxrRedirection() throws Exception {
        //given
        Long id = 1L;
        String password = "mockPassword";
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(password);

        //when
        ResultActions resultActions = mockMvc.perform(post("/v1/member/details/" + id + "/delete-account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string("회원 탈퇴에 성공했습니다."));
    }


    private UpdatePasswordDto getTestUpdatePasswordDto() {
        UpdatePasswordDto updatePasswordDto = new UpdatePasswordDto();
        updatePasswordDto.setOldPasswd("old123!!");
        updatePasswordDto.setNewPasswd("new123!!");
        return updatePasswordDto;
    }

    private UserDto createTestUser() {
        return UserDto.builder()
                .loginId("testuser")
                .username("테스트")
                .userPasswd("test123!")
                .nickname("imtest")
                .email("test@example.com")
                .birthDate("19970908")
                .build();
    }
}