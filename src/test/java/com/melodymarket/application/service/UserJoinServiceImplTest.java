package com.melodymarket.application.service;

import com.melodymarket.application.dto.UserDto;
import com.melodymarket.infrastructure.mybatis.exception.MybatisDuplicateKeyException;
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


    @Test
    @DisplayName("존재하는 유저아이디 예외 발생 체크")
    void givenExistUserId_whenCheckUserIdDuplication_thenThrowsException() {
        //given
        String existUserId = "elephant";

        //when
        //then
        assertThrows(MybatisDuplicateKeyException.class
                , () -> userJoinService.checkUserIdDuplication(existUserId));
    }

    @Test
    @DisplayName("존재하지 않는 유저아이디 예외 미발생 체크")
    void givenNotExistUserId_whenCheckUserIdDuplication_thenDoseNotThrowException() {
        //given
        String NotExistUserId = "not_exist";

        //when
        //then
        assertDoesNotThrow(() -> userJoinService.checkUserIdDuplication(NotExistUserId));
    }

    @Test
    @DisplayName("존재하는 닉네임 예외 발생 체크")
    void givenExistNickname_whencheckNicknameDuplication_thenThrowsException() {
        //given
        String existNickname = "hello";

        //when
        //then
        assertThrows(MybatisDuplicateKeyException.class
                , () -> userJoinService.checkNicknameDuplication(existNickname));
    }

    @Test
    @DisplayName("존재하지 않는 닉네임 예외 미발생 체크")
    void givenNotExistNickname_whencheckNicknameDuplication_thenDoseNotThrowException() {
        //given
        String notExistNickname = "not_exist";

        //when
        //then
        assertDoesNotThrow(() -> userJoinService.checkNicknameDuplication(notExistNickname));
    }

    @Test
    @DisplayName("존재하지 않는 유저 정보 회원가입 예외 미발생 테스트")
    void givenNotExsistUserDto_whenSaveUser_thenDoseNotThrowException() {
        //given
        UserDto testUser = createTestUser();

        //when
        //then
        assertDoesNotThrow(()->userJoinService.signUpUser(testUser));
    }

    @Test
    @DisplayName("존재하는 유저 정보 회원가입 예외 발생 테스트")
    void givenNotExsistUserDto_whenSaveUser_thenThrowsException() {
        //given
        UserDto testUser = createTestUser();

        // when
        userJoinService.signUpUser(testUser);


        //then
        assertThrows(MybatisDuplicateKeyException.class,()->userJoinService.signUpUser(testUser));
    }

    private UserDto createTestUser() {
        UserDto userDto = new UserDto();
        userDto.setUserId("testuser");
        userDto.setUserPasswd("test123!");
        userDto.setNickname("imtest");
        userDto.setBirthDate("19970908");
        userDto.setEmail("test@example.com");

        return userDto;
    }
}