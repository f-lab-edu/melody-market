package com.melodymarket.admin.mapper;

import com.melodymarket.admin.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    boolean existByUserId(String userId);

    boolean existByNickname(String nickname);

    void saveUser(UserDto userDto);
}
