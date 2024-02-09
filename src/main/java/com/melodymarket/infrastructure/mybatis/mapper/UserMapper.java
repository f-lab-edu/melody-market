package com.melodymarket.infrastructure.mybatis.mapper;

import com.melodymarket.application.dto.UpdateUserDto;
import com.melodymarket.domain.user.model.UserModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    boolean existByLoginId(String loginId);
    boolean existByNickname(String nickname);
    void saveUser(UserModel userModel);
    UserModel findUser(String loginId);
    UserModel getUserInfo(Long id);
    void updatePassword(Long id, String password);
    void updateUserInfo(Long id, UpdateUserDto updateUserDto);
    void deleteUserAccount(Long id);
}
