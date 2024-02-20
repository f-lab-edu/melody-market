package com.melodymarket.application.user.service;

import com.melodymarket.application.user.dto.UpdatePasswordDto;
import com.melodymarket.application.user.dto.UpdateUserDto;
import com.melodymarket.application.user.dto.UserDto;
import com.melodymarket.common.exception.PasswordMismatchException;
import com.melodymarket.common.exception.PasswordSameException;
import com.melodymarket.domain.user.entity.User;
import com.melodymarket.infrastructure.exception.DataNotFoundException;
import com.melodymarket.infrastructure.jpa.user.repository.UserRepository;
import com.melodymarket.presentation.admin.dto.UserResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@DisplayName("회원 관리 서비스 테스트")
class UserInfoManageServiceImplTest {

    @Autowired
    UserInfoManageService userInfoManageService;
    @Autowired
    UserJoinServiceImpl userJoinServiceImpl;
    @Autowired
    CryptPasswordService cryptPasswordService;
    @Autowired
    UserRepository userRepository;
    UserDto userDto;
    User userSelect;
    String sessionId = "testSessionId";

    @BeforeEach
    void insert() {
        this.userDto = createTestUser();
        userJoinServiceImpl.signUpUser(userDto, sessionId);
        this.userSelect = userRepository.findByLoginId("testuser").orElse(null);
    }

    @Test
    @DisplayName("존재하는 유저 ID의 유저 정보 조회")
    void givenExistUserId_whenGetUserDetails_thenGetUserInfo() {
        //given
        Long id = this.userSelect.getId();

        //when
        UserResponseDto returnUser = userInfoManageService.getUserDetails(id);

        //then
        Assertions.assertThat(returnUser).isNotNull();
    }

    @Test
    @DisplayName("존재하지 않는 유저 ID의 유저 정보 조회 예외 발생 테스트")
    void givenNotExistUserId_whenGetUserDetails_thenThrowException() {
        //given
        Long id = 999L;

        //when
        Exception exception =
                assertThrows(Exception.class, () -> userInfoManageService.getUserDetails(id));

        //then
        assertTrue(exception instanceof DataNotFoundException);
    }

    @Test
    @DisplayName("정확한 이전 비밀번호 입력과 새로운 비밀번호 입력 후 비밀번호 변경 테스트")
    void givenCorrectOldPasswdAndNewPasswd_whenModifyUserPasswd_thenSelectedNewPasswordIsSame() {
        //given
        Long id = this.userSelect.getId();
        UpdatePasswordDto updatePasswordDto = getTestUpdatePasswordDto();

        //when
        userInfoManageService.modifyUserPassword(id, updatePasswordDto);

        //then
        updatePasswordDto.setOldPassword(updatePasswordDto.getNewPassword());
        Exception exception = assertThrows(Exception.class, () -> userInfoManageService.modifyUserPassword(id, updatePasswordDto));
        assertTrue(exception instanceof PasswordSameException);
    }

    @Test
    @DisplayName("잘못 된 이전 비밀번호 입력과 새로운 비밀번호 입력 후 비밀번호 변경 테스트")
    void givenIncorrectOldPasswdAndNewPasswd_whenModifyUserPasswd_thenThrowPasswordMismatchException() {
        //given
        Long id = this.userSelect.getId();
        UpdatePasswordDto updatePasswordDto = getTestUpdatePasswordDto();
        updatePasswordDto.setOldPassword("incorrect");

        //when
        Exception exception =
                assertThrows(Exception.class, () -> userInfoManageService.modifyUserPassword(id, updatePasswordDto));

        //then
        assertTrue(exception instanceof PasswordMismatchException);
    }

    @Test
    @DisplayName("알 수 없는 userId에 대한 비밀번호 변경 테스트")
    void givenUnknownUserId_whenModifyUserPasswd_thenThrowDataNotFoundException() {
        //given
        Long id = 999L;
        UpdatePasswordDto updatePasswordDto = getTestUpdatePasswordDto();

        //when
        Exception exception =
                assertThrows(Exception.class, () -> userInfoManageService.modifyUserPassword(id, updatePasswordDto));

        //then
        assertTrue(exception instanceof DataNotFoundException);
    }

    @Test
    @DisplayName("닉네임 변경 테스트")
    void givenNewNickname_whenModifyUserDetails_thenSelectedNewNicknameIsSame() {
        //given
        Long id = this.userSelect.getId();
        UpdateUserDto updateUserDto = getTestUpdateUserDto("nickname");

        //when
        userInfoManageService.modifyUserDetails(id, updateUserDto);
        UserResponseDto userDto = userInfoManageService.getUserDetails(id);

        //then
        Assertions.assertThat(userDto.getNickname()).isEqualTo(updateUserDto.getNickname());
    }

    @Test
    @DisplayName("이메일 변경 테스트")
    void givenNewEmail_whenModifyUserDetails_thenSelectedNewEmailIsSame() {
        //given
        Long id = this.userSelect.getId();
        UpdateUserDto updateUserDto = getTestUpdateUserDto("email");

        //when
        userInfoManageService.modifyUserDetails(id, updateUserDto);
        UserResponseDto userDto = userInfoManageService.getUserDetails(id);

        //then
        Assertions.assertThat(userDto.getEmail()).isEqualTo(updateUserDto.getEmail());
    }

    @Test
    @DisplayName("닉네임, 이메일 동시 변경 테스트")
    void givenNewNicknameAndNewEmail_whenModifyUserDetails_thenSelectedNewNicknameAndNewEmailIsSame() {
        //given
        Long id = this.userSelect.getId();
        UpdateUserDto updateUserDto = getTestUpdateUserDto("all");

        //when
        userInfoManageService.modifyUserDetails(id, updateUserDto);
        UserResponseDto userDto = userInfoManageService.getUserDetails(id);

        //then
        org.junit.jupiter.api.Assertions.assertAll(
                () -> assertEquals(userDto.getNickname(), updateUserDto.getNickname()),
                () -> assertEquals(userDto.getEmail(), updateUserDto.getEmail())
        );
    }

    @Test
    @DisplayName("회원 삭제 후 삭제 확인 테스트")
    void givenUserIdAndUserPassword_whenDeleteUser_thenCantSelectUserInfo() {
        //given
        Long id = this.userSelect.getId();

        //when
        userInfoManageService.deleteUser(id, userDto.getUserPassword());
        Exception exception =
                assertThrows(Exception.class, () -> userInfoManageService.getUserDetails(id));

        //then
        assertTrue(exception instanceof DataNotFoundException);
    }

    @Test
    @DisplayName("존재하지 않는 회원 삭제 요청 시 예외 발생 테스트")
    void givenUnknownUserIdAndUserPassword_whenDeleteUser_thenDataNotFoundException() {
        //given
        Long id = 999L;

        //when
        Exception exception =
                assertThrows(Exception.class,
                        () -> userInfoManageService.deleteUser(id, userDto.getUserPassword()));

        //then
        assertTrue(exception instanceof DataNotFoundException);

    }

    @Test
    @DisplayName("회원 삭제 요청 시 틀린 비밀번호 예외 발생 테스트")
    void givenUserIdAndIncorrectUserPassword_whenDeleteUser_thenThrowsPasswordMissMatchException() {
        //given
        Long id = this.userSelect.getId();
        String incorrectPassword = "incorrect~!";

        //when
        Exception exception =
                assertThrows(Exception.class,
                        () -> userInfoManageService.deleteUser(id, incorrectPassword));

        //then
        assertTrue(exception instanceof PasswordMismatchException);
    }


    private UpdateUserDto getTestUpdateUserDto(String target) {
        UpdateUserDto updateUserDto = new UpdateUserDto();
        if (target.equals("nickname")) {
            updateUserDto.setNickname("newnickname");
        } else if (target.equals("email")) {
            updateUserDto.setEmail("new@test.com");
        } else {
            updateUserDto.setNickname("newnickname");
            updateUserDto.setEmail("new@test.com");
        }

        return updateUserDto;
    }

    private UpdatePasswordDto getTestUpdatePasswordDto() {
        UpdatePasswordDto updatePasswordDto = new UpdatePasswordDto();
        updatePasswordDto.setOldPassword("test123!");
        updatePasswordDto.setNewPassword("new123!!");
        return updatePasswordDto;
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