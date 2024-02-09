package com.melodymarket.application.service;

import com.melodymarket.application.dto.UserDto;
import com.melodymarket.infrastructure.exception.DataDuplicateKeyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

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

        //when
        Exception exception = assertThrows(Exception.class,
                () -> userJoinService.checkUserIdDuplication(existUserId));

        //then
        assertTrue(exception instanceof DataDuplicateKeyException);
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
        Exception exception = assertThrows(Exception.class,
                () -> userJoinService.checkNicknameDuplication(existNickname));

        //then
        assertTrue(exception instanceof DataDuplicateKeyException);
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
        Exception exception = assertThrows(Exception.class,
                () -> userJoinService.signUpUser(testUser));

        //then
        assertTrue(exception instanceof DataDuplicateKeyException);
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

    public UserDto createTestNewUser() {
        return UserDto.builder()
                .loginId("testuser2")
                .username("테스트2")
                .userPassword("test123!")
                .nickname("imtest2")
                .email("test2@example.com")
                .birthDate("19970908")
                .build();
    }

}