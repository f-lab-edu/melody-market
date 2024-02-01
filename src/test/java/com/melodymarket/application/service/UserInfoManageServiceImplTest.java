package com.melodymarket.application.service;

import com.melodymarket.application.dto.UserDto;
import com.melodymarket.infrastructure.exception.DataNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@DisplayName("회원 관리 서비스 테스트")
class UserInfoManageServiceImplTest {

    @Autowired
    UserInfoManageService userInfoManageService;

    @Autowired
    UserJoinService userJoinService;

    UserDto userDto;

    @BeforeEach
    void insert() {
        this.userDto = createTestUser();
        userJoinService.signUpUser(userDto);
    }

    @Test
    @DisplayName("존재하는 유저 ID의 유저 정보 조회")
    void givenExistUserId_whenGetUserDetails_thenGetUserInfo() {
        //given
        String userId = this.userDto.getUserId();

        //when
        UserDto returnUser = userInfoManageService.getUserDetails(userId);

        //then
        Assertions.assertThat(returnUser).isNotNull();
    }

    @Test
    @DisplayName("존재하지 않는 유저 ID의 유저 정보 조회 예외 발생 테스트")
    void givenNotExistUserId_whenGetUserDetails_thenThrowException() {
        //given
        String userId = "anonymous";

        //when
        DataNotFoundException exception =
                assertThrows(DataNotFoundException.class,()->userInfoManageService.getUserDetails(userId));

        //then
        Assertions.assertThat(exception.getMessage()).isEqualTo("유저 정보를 조회할 수 없습니다.");
    }

    private UserDto createTestUser() {
        UserDto userDto = new UserDto();
        userDto.setUserId("testuser");
        userDto.setUsername("테스트");
        userDto.setUserPasswd("test123!");
        userDto.setNickname("imtest");
        userDto.setBirthDate("19970908");
        userDto.setEmail("test@example.com");

        return userDto;
    }
}