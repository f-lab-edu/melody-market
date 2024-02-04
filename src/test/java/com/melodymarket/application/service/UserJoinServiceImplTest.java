package com.melodymarket.application.service;

import com.melodymarket.application.dto.UserDto;
import com.melodymarket.infrastructure.exception.DataDuplicateKeyException;
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
class UserJoinServiceImplTest {


    @Autowired
    UserJoinService userJoinService;
    UserDto userDto;

    @BeforeEach
    void insert() {
        this.userDto = createTestUser();
        userJoinService.signUpUser(userDto);
    }

    @Test
    @DisplayName("존재하는 유저아이디 예외 발생 체크")
    void givenExistUserId_whenCheckUserIdDuplication_thenThrowsException() {
        //given
        String existUserId = "testuser";

        //when & then
        assertThrows(DataDuplicateKeyException.class,
                () -> userJoinService.checkUserIdDuplication(existUserId));
    }

    @Test
    @DisplayName("존재하지 않는 유저아이디 예외 미발생 체크")
    void givenNotExistUserId_whenCheckUserIdDuplication_thenDoseNotThrowException() {
        //given
        String NotExistUserId = "not_exist";

        //when & then
        assertDoesNotThrow(() -> userJoinService.checkUserIdDuplication(NotExistUserId));
    }

    @Test
    @DisplayName("존재하는 닉네임 예외 발생 체크")
    void givenExistNickname_whenCheckNicknameDuplication_thenThrowsException() {
        //given
        String existNickname = "imtest";

        //when & then
        assertThrows(DataDuplicateKeyException.class,
                () -> userJoinService.checkNicknameDuplication(existNickname));
    }

    @Test
    @DisplayName("존재하지 않는 닉네임 예외 미발생 체크")
    void givenNotExistNickname_whenCheckNicknameDuplication_thenDoseNotThrowException() {
        //given
        String notExistNickname = "not_exist";

        //when & then
        assertDoesNotThrow(() -> userJoinService.checkNicknameDuplication(notExistNickname));
    }

    @Test
    @DisplayName("존재하지 않는 유저 정보 회원가입 예외 미발생 테스트")
    void givenNotExistUserDto_whenSaveUser_thenDoseNotThrowException() {
        //given
        UserDto testUser = createTestNewUser();

        //when & then
        assertDoesNotThrow(() -> userJoinService.signUpUser(testUser));
    }

    @Test
    @DisplayName("존재하는 유저 정보 회원가입 예외 발생 테스트")
    void givenNotExistUserDto_whenSaveUser_thenThrowsException() {
        //given
        UserDto testUser = createTestUser();

        // when
        DataDuplicateKeyException exception = assertThrows(DataDuplicateKeyException.class,
                () -> userJoinService.signUpUser(testUser));

        //then
        Assertions.assertThat(exception.getMessage()).isEqualTo("이미 가입 된 회원 정보 입니다.");
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

    public UserDto createTestNewUser() {
        return UserDto.builder()
                .loginId("testuser2")
                .username("테스트2")
                .userPasswd("test123!")
                .nickname("imtest2")
                .email("test2@example.com")
                .birthDate("19970908")
                .build();
    }

}