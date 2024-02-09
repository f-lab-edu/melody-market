package com.melodymarket.application.service;

import com.melodymarket.application.dto.UpdatePasswordDto;
import com.melodymarket.application.dto.UpdateUserDto;
import com.melodymarket.application.dto.UserDto;
import com.melodymarket.common.exception.PasswordMismatchException;
import com.melodymarket.domain.user.model.UserModel;
import com.melodymarket.infrastructure.exception.DataNotFoundException;
import com.melodymarket.infrastructure.mybatis.mapper.UserMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    UserMapper userMapper;
    UserDto userDto;
    UserModel userModel;

    @BeforeEach
    void insert() {
        this.userDto = createTestUser();
        userJoinServiceImpl.signUpUser(userDto);
        this.userModel = userMapper.findUser("testuser");
    }

    @Test
    @DisplayName("존재하는 유저 ID의 유저 정보 조회")
    void givenExistUserId_whenGetUserDetails_thenGetUserInfo() {
        //given
        Long id = this.userModel.getId();

        //when
        UserDto returnUser = userInfoManageService.getUserDetails(id);

        //then
        Assertions.assertThat(returnUser).isNotNull();
    }

    @Test
    @DisplayName("존재하지 않는 유저 ID의 유저 정보 조회 예외 발생 테스트")
    void givenNotExistUserId_whenGetUserDetails_thenThrowException() {
        //given
        Long id = 999L;

        //when
        DataNotFoundException exception =
                assertThrows(DataNotFoundException.class, () -> userInfoManageService.getUserDetails(id));

        //then
        Assertions.assertThat(exception.getMessage()).isEqualTo("유저 정보를 조회할 수 없습니다.");
    }

    @Test
    @DisplayName("정확한 이전 비밀번호 입력과 새로운 비밀번호 입력 후 비밀번호 변경 테스트")
    void givenCorrectOldPasswdAndNewPasswd_whenModifyUserPasswd_thenSelectedNewPasswordIsSame() {
        //given
        Long id = this.userModel.getId();
        UpdatePasswordDto updatePasswordDto = getTestUpdatePasswordDto();

        //when
        userInfoManageService.modifyUserPassword(id, updatePasswordDto);
        UserModel userModel = userMapper.getUserInfo(id);

        //then
        Assertions.assertThat(cryptPasswordService
                        .isPasswordMatch(updatePasswordDto.getNewPassword(), userModel.getUserPassword()))
                .isTrue();

    }

    @Test
    @DisplayName("잘못 된 이전 비밀번호 입력과 새로운 비밀번호 입력 후 비밀번호 변경 테스트")
    void givenIncorrectOldPasswdAndNewPasswd_whenModifyUserPasswd_thenThrowPasswordMismatchException() {
        //given
        Long id = this.userModel.getId();
        UpdatePasswordDto updatePasswordDto = getTestUpdatePasswordDto();
        updatePasswordDto.setOldPassword("incorrect");

        //when
        PasswordMismatchException exception =
                assertThrows(PasswordMismatchException.class, () -> userInfoManageService.modifyUserPassword(id, updatePasswordDto));

        //then
        Assertions.assertThat(exception.getMessage()).isEqualTo("비밀번호가 일치하지 않습니다.");
    }

    @Test
    @DisplayName("알 수 없는 userId에 대한 비밀번호 변경 테스트")
    void givenUnknownUserId_whenModifyUserPasswd_thenThrowDataNotFoundException() {
        //given
        Long id = 999L;
        UpdatePasswordDto updatePasswordDto = getTestUpdatePasswordDto();

        //when
        DataNotFoundException exception =
                assertThrows(DataNotFoundException.class, () -> userInfoManageService.modifyUserPassword(id, updatePasswordDto));

        //then
        Assertions.assertThat(exception.getMessage()).isEqualTo("알 수 없는 유저 정보에 대한 요청 입니다.");
    }

    @Test
    @DisplayName("닉네임 변경 테스트")
    void givenNewNickname_whenModifyUserDetails_thenSelectedNewNicknameIsSame() {
        //given
        Long id = this.userModel.getId();
        UpdateUserDto updateUserDto = getTestUpdateUserDto("nickname");

        //when
        userInfoManageService.modifyUserDetails(id, updateUserDto);
        UserModel userModel = userMapper.getUserInfo(id);

        //then
        Assertions.assertThat(userModel.getNickname()).isEqualTo(updateUserDto.getNickname());
    }

    @Test
    @DisplayName("이메일 변경 테스트")
    void givenNewEmail_whenModifyUserDetails_thenSelectedNewEmailIsSame() {
        //given
        Long id = this.userModel.getId();
        UpdateUserDto updateUserDto = getTestUpdateUserDto("email");

        //when
        userInfoManageService.modifyUserDetails(id, updateUserDto);
        UserModel userModel = userMapper.getUserInfo(id);

        //then
        Assertions.assertThat(userModel.getEmail()).isEqualTo(updateUserDto.getEmail());
    }

    @Test
    @DisplayName("닉네임, 이메일 동시 변경 테스트")
    void givenNewNicknameAndNewEmail_whenModifyUserDetails_thenSelectedNewNicknameAndNewEmailIsSame() {
        //given
        Long id = this.userModel.getId();
        UpdateUserDto updateUserDto = getTestUpdateUserDto("all");

        //when
        userInfoManageService.modifyUserDetails(id, updateUserDto);
        UserModel userModel = userMapper.getUserInfo(id);

        //then
        org.junit.jupiter.api.Assertions.assertAll(
                () -> assertEquals(userModel.getNickname(), updateUserDto.getNickname()),
                () -> assertEquals(userModel.getEmail(), updateUserDto.getEmail())
        );
    }

    @Test
    @DisplayName("회원 삭제 후 삭제 확인 테스트")
    void givenUserIdAndUserPassword_whenDeleteUserAccount_thenCantSelectUserInfo() {
        //given
        Long id = this.userModel.getId();

        //when
        userInfoManageService.deleteUserAccount(id, userDto.getUserPassword());
        DataNotFoundException exception =
                assertThrows(DataNotFoundException.class, () -> userInfoManageService.getUserDetails(id));

        //then
        Assertions.assertThat(exception.getMessage()).isEqualTo("유저 정보를 조회할 수 없습니다.");
    }

    @Test
    @DisplayName("존재하지 않는 회원 삭제 요청 시 예외 발생 테스트")
    void givenUnknownUserIdAndUserPassword_whenDeleteUserAccount_thenDataNotFoundException() {
        //given
        Long id = 999L;

        //when
        DataNotFoundException exception =
                assertThrows(DataNotFoundException.class,
                        () -> userInfoManageService.deleteUserAccount(id, userDto.getUserPassword()));

        //then
        Assertions.assertThat(exception.getMessage()).isEqualTo("존재하지 않는 회원입니다.");
    }

    @Test
    @DisplayName("회원 삭제 요청 시 틀린 비밀번호 예외 발생 테스트")
    void givenUserIdAndIncorrectUserPassword_whenDeleteUserAccount_thenThrowsPasswordMissMatchException() {
        //given
        Long id = this.userModel.getId();
        String incorrectPassword = "incorrect~!";

        //when
        PasswordMismatchException exception =
                assertThrows(PasswordMismatchException.class,
                        () -> userInfoManageService.deleteUserAccount(id, incorrectPassword));

        //then
        Assertions.assertThat(exception.getMessage()).isEqualTo("비밀번호가 일치하지 않습니다.");
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