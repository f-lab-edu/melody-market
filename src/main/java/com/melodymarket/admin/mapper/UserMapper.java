package com.melodymarket.admin.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    boolean existByUserId(String userId);

    boolean existByNickname(String nickname);
}
