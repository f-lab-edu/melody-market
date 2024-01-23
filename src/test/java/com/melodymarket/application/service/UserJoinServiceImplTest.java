package com.melodymarket.application.service;

import com.melodymarket.application.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UserJoinServiceImplTest {


    @Autowired
    UserJoinService userJoinService;


    @Test
    @DisplayName("존재하는 유저아이디 중복 체크")
    void givenExistUserId_whenCheckUserIdDuplication_thenTrue() {
        //given
        String existUserId = "elephant";

        //when
        boolean result = userJoinService.checkUserIdDuplication(existUserId);

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("존재하지 않는 유저아이디 중복 체크")
    void givenNotExistUserId_whenCheckUserIdDuplication_thenReturnFalse() {
        //given
        String NotExistUserId = "not_exist";

        //when
        boolean result = userJoinService.checkUserIdDuplication(NotExistUserId);

        //then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("존재하는 닉네임 중복 체크")
    void givenExistNickname_whencheckNicknameDuplication_thenTrue() {
        //given
        String existNickname = "hello";

        //when
        boolean result = userJoinService.checkNicknameDuplication(existNickname);

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("존재하지 않는 닉네임 중복 체크")
    void givenNotExistNickname_whencheckNicknameDuplication_thenReturnFalse() {
        //given
        String notExistNickname = "not_exist";

        //when
        boolean result = userJoinService.checkNicknameDuplication(notExistNickname);

        //then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("회원가입 테스트")
    void givenUserDto_whenSaveUser_thenReturnTrue() {
        //given
        UserDto testUser = new UserDto();
        setTestUser(testUser);

        //when
        boolean result = userJoinService.signUpUser(testUser);

        //then
        assertThat(result).isTrue();
    }

    private void setTestUser(UserDto testUser) {
        testUser.setUserId("testuser");
        testUser.setUserPasswd("test123!");
        testUser.setNickname("imtest");
        testUser.setBirthDate("19970908");
        testUser.setEmail("test@example.com");
    }
}