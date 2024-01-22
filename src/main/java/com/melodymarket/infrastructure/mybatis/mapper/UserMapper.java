package com.melodymarket.infrastructure.mybatis.mapper;

import com.melodymarket.application.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    boolean existByUserId(String userId);

    boolean existByNickname(String nickname);

    void saveUser(UserDto userDto);
}
