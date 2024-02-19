package com.melodymarket.presentation.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melodymarket.application.user.dto.UpdatePasswordDto;
import com.melodymarket.application.user.dto.UpdateUserDto;
import com.melodymarket.application.user.dto.UserDto;
import com.melodymarket.application.user.service.UserInfoManageServiceImpl;
import com.melodymarket.presentation.admin.dto.UserResponseDto;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("회원조희 API 테스트")
@WithMockUser(roles = "USER")
@WebMvcTest(ManageUserController.class)
class ManageUserControllerTest {

    @MockBean
    UserInfoManageServiceImpl userInfoManageService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ManageUserController manageUserController;

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
        Mockito.when(userInfoManageService.getUserDetails(id))
                .thenReturn(UserResponseDto.builder()
                        .username(userDto.getUsername())
                        .nickname(userDto.getNickname())
                        .birthDate(userDto.getBirthDate())
                        .email(userDto.getEmail()).build());
        ResultActions resultActions = mockMvc.perform(get("/v1/user/details/" + id));

        //then
        resultActions.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value("OK"))
                .andExpect(jsonPath("$.message").value("유저 정보 조회에 성공했습니다."))
                .andExpect(jsonPath("$.data.username").value("테스트"))
                .andExpect(jsonPath("$.data.nickname").value("imtest"))
                .andExpect(jsonPath("$.data.birthDate").value("19970908"))
                .andExpect(jsonPath("$.data.email").value("test@example.com"));
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
        ResultActions resultActions = mockMvc.perform(post("/v1/user/details/" + id + "/update-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTestDto));

        //then
        resultActions.andExpect(status().isOk());
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
        ResultActions resultActions = mockMvc.perform(post("/v1/user/details/" + id + "/update-user-info")
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
        ResultActions resultActions = mockMvc.perform(post("/v1/user/delete/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        //then
        resultActions.andExpect(status().isOk());
    }


    private UpdatePasswordDto getTestUpdatePasswordDto() {
        UpdatePasswordDto updatePasswordDto = new UpdatePasswordDto();
        updatePasswordDto.setOldPassword("old123!!");
        updatePasswordDto.setNewPassword("new123!!");
        return updatePasswordDto;
    }

    private UserDto createTestUser() {
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