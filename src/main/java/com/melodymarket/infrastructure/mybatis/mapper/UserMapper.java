package com.melodymarket.infrastructure.mybatis.mapper;

import com.melodymarket.domain.user.model.Account;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    boolean existByLoginId(String loginId);

    boolean existByNickname(String nickname);

    void saveUser(Account account);

    Account findUser(String loginId);

    Account getUserInfo(Long userId);
}
