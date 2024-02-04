package com.melodymarket.application.service;

import com.melodymarket.application.dto.UpdatePasswordDto;
import com.melodymarket.application.dto.UserDto;
import com.melodymarket.common.exception.PasswordMismatchException;
import com.melodymarket.infrastructure.exception.DataNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@DisplayName("회원 관리 서비스 테스트")
class UserInfoManageServiceImplTest {

    @Autowired
    UserInfoManageService userInfoManageService;

    @Autowired
    UserJoinServiceImpl userJoinServiceImpl;

    UserDto userDto;

    @BeforeEach
    void insert() {
        this.userDto = createTestUser();
        userJoinServiceImpl.signUpUser(userDto);
    }

    @Test
    @DisplayName("존재하는 유저 ID의 유저 정보 조회")
    void givenExistUserId_whenGetUserDetails_thenGetUserInfo() {
        //given
        Long userId = 1L;

        //when
        UserDto returnUser = userInfoManageService.getUserDetails(userId);

        //then
        Assertions.assertThat(returnUser).isNotNull();
    }

    @Test
    @DisplayName("존재하지 않는 유저 ID의 유저 정보 조회 예외 발생 테스트")
    void givenNotExistUserId_whenGetUserDetails_thenThrowException() {
        //given
        Long userId = 999L;

        //when
        DataNotFoundException exception =
                assertThrows(DataNotFoundException.class, () -> userInfoManageService.getUserDetails(userId));

        //then
        Assertions.assertThat(exception.getMessage()).isEqualTo("유저 정보를 조회할 수 없습니다.");
    }

    @Test
    @DisplayName("정확한 이전 비밀번호 입력과 새로운 비밀번호 입력 후 비밀번호 변경 테스트")
    void givenCorrectOldPasswdAndNewPasswd_whenModifyUserPasswd_thenSccuessUpdate() {
        //given
        Long userId = 1L;
        UpdatePasswordDto updatePasswordDto = getTestUpdatePasswordDto();

        //when & then
        assertDoesNotThrow(()-> userInfoManageService.modifyUserPassword(userId, updatePasswordDto));
    }

    @Test
    @DisplayName("잘못 된 이전 비밀번호 입력과 새로운 비밀번호 입력 후 비밀번호 변경 테스트")
    void givenIncorrectOldPasswdAndNewPasswd_whenModifyUserPasswd_thenThrowPasswordMismatchException() {
        //given
        Long userId = 1L;
        UpdatePasswordDto updatePasswordDto = getTestUpdatePasswordDto();
        updatePasswordDto.setOldPasswd("incorrect");

        //when
        PasswordMismatchException exception =
                assertThrows(PasswordMismatchException.class, () -> userInfoManageService.modifyUserPassword(userId, updatePasswordDto));
        //then
        Assertions.assertThat(exception.getMessage()).isEqualTo("비밀번호가 일치하지 않습니다.");
    }

    @Test
    @DisplayName("알 수 없는 userId에 대한 비밀번호 변경 테스트")
    void givenUnknownUserId_whenModifyUserPasswd_thenThrowDataNotFoundException() {
        //given
        Long userId = 999L;
        UpdatePasswordDto updatePasswordDto = getTestUpdatePasswordDto();

        //when
        DataNotFoundException exception =
                assertThrows(DataNotFoundException.class, () -> userInfoManageService.modifyUserPassword(userId, updatePasswordDto));
        //then
        Assertions.assertThat(exception.getMessage()).isEqualTo("알 수 없는 유저 정보에 대한 요청 입니다.");
    }

    private UpdatePasswordDto getTestUpdatePasswordDto() {
        UpdatePasswordDto updatePasswordDto = new UpdatePasswordDto();
        updatePasswordDto.setOldPasswd("old123!!");
        updatePasswordDto.setNewPasswd("new123!!");
        return updatePasswordDto;
    }

    public UserDto createTestUser() {
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