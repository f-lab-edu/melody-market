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