package com.melodymarket.application.service;

import com.melodymarket.application.dto.UpdatePasswordDto;
import com.melodymarket.application.dto.UpdateUserDto;
import com.melodymarket.application.dto.UserDto;
import com.melodymarket.common.exception.PasswordMismatchException;
import com.melodymarket.domain.user.model.Account;
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
    Long id;

    @BeforeEach
    void insert() {
        this.userDto = createTestUser();
        userJoinServiceImpl.signUpUser(userDto);
        Account account = userMapper.findUser("testuser");
        id = account.getId();
    }

    @Test
    @DisplayName("존재하는 유저 ID의 유저 정보 조회")
    void givenExistUserId_whenGetUserDetails_thenGetUserInfo() {
        //given
        Long id = this.id;

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
        Long id = this.id;
        UpdatePasswordDto updatePasswordDto = getTestUpdatePasswordDto();

        //when
        userInfoManageService.modifyUserPassword(id, updatePasswordDto);
        Account account = userMapper.getUserInfo(id);

        //then
        Assertions.assertThat(cryptPasswordService
                        .isPasswordMatch(updatePasswordDto.getNewPasswd(),account.getUserPasswd()))
                .isTrue();

    }

    @Test
    @DisplayName("잘못 된 이전 비밀번호 입력과 새로운 비밀번호 입력 후 비밀번호 변경 테스트")
    void givenIncorrectOldPasswdAndNewPasswd_whenModifyUserPasswd_thenThrowPasswordMismatchException() {
        //given
        Long id = this.id;
        UpdatePasswordDto updatePasswordDto = getTestUpdatePasswordDto();
        updatePasswordDto.setOldPasswd("incorrect");

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
        Long id = this.id;
        UpdateUserDto updateUserDto = getTestUpdateUserDto("nickname");

        //when
        userInfoManageService.modifyUserDetails(id, updateUserDto);
        Account account = userMapper.getUserInfo(id);

        //then
        Assertions.assertThat(account.getNickname()).isEqualTo(updateUserDto.getNickname());
    }

    @Test
    @DisplayName("이메일 변경 테스트")
    void givenNewEmail_whenModifyUserDetails_thenSelectedNewEmailIsSame() {
        //given
        Long id = this.id;
        UpdateUserDto updateUserDto = getTestUpdateUserDto("email");

        //when
        userInfoManageService.modifyUserDetails(id, updateUserDto);
        Account account = userMapper.getUserInfo(id);

        //then
        Assertions.assertThat(account.getEmail()).isEqualTo(updateUserDto.getEmail());
    }

    @Test
    @DisplayName("닉네임, 이메일 동시 변경 테스트")
    void givenNewNicknameAndNewEmail_whenModifyUserDetails_thenSelectedNewNicknameAndNewEmailIsSame() {
        //given
        Long id = this.id;
        UpdateUserDto updateUserDto = getTestUpdateUserDto("all");

        //when
        userInfoManageService.modifyUserDetails(id, updateUserDto);
        Account account = userMapper.getUserInfo(id);

        //then
        org.junit.jupiter.api.Assertions.assertAll(
                () -> assertEquals(account.getNickname(), updateUserDto.getNickname()),
                () -> assertEquals(account.getEmail(), updateUserDto.getEmail())
        );
    }



    private UpdateUserDto getTestUpdateUserDto(String target) {
        UpdateUserDto updateUserDto = new UpdateUserDto();
        if(target.equals("nickname")) {
            updateUserDto.setNickname("newnickname");
        }
        else if(target.equals("email")) {
            updateUserDto.setEmail("new@test.com");
        }
        else {
            updateUserDto.setNickname("newnickname");
            updateUserDto.setEmail("new@test.com");
        }

        return updateUserDto;
    }
    private UpdatePasswordDto getTestUpdatePasswordDto() {
        UpdatePasswordDto updatePasswordDto = new UpdatePasswordDto();
        updatePasswordDto.setOldPasswd("test123!");
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