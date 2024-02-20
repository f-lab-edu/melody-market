package com.melodymarket.application.user.service;

import com.melodymarket.application.user.dto.UpdatePasswordDto;
import com.melodymarket.application.user.dto.UpdateUserDto;
import com.melodymarket.common.exception.PasswordMismatchException;
import com.melodymarket.common.exception.PasswordSameException;
import com.melodymarket.domain.user.entity.User;
import com.melodymarket.infrastructure.exception.DataNotFoundException;
import com.melodymarket.infrastructure.jpa.user.repository.UserRepository;
import com.melodymarket.presentation.admin.dto.UserResponseDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserInfoManageServiceImpl implements UserInfoManageService {

    UserRepository userRepository;
    CryptPasswordService cryptPasswordService;

    @Override
    public UserResponseDto getUserDetails(Long id) {
        log.debug("유저 정보 조회={}", id);
        return UserResponseDto.from(getUserEntity(id));
    }

    @Override
    public void modifyUserPassword(Long id, UpdatePasswordDto updatePasswordDto) {
        User user = getUserEntity(id);
        if (!cryptPasswordService.isPasswordMatch(updatePasswordDto.getOldPassword(),
                user.getUserPassword())) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        } else if (cryptPasswordService.isPasswordMatch(updatePasswordDto.getNewPassword(),
                user.getUserPassword())) {
            throw new PasswordSameException("새 비밀번호는 현재 비밀번호와 다르게 설정해야 합니다.");
        }

        user.changePassword(updatePasswordDto.getNewPassword(), cryptPasswordService);
        userRepository.save(user);
    }

    @Override
    public void modifyUserDetails(Long id, UpdateUserDto updateUserDto) {
        log.debug("유저 정보 수정={}", id);
        userRepository.save(getUserEntity(id).modifyValueSetUserEntity(updateUserDto));
    }

    @Override
    public void deleteUser(Long id, String password) {
        User user = getUserEntity(id);
        if (!cryptPasswordService.isPasswordMatch(password,
                user.getUserPassword())) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }
        userRepository.deleteById(id);
    }

    public User getUserEntity(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("유저 정보를 조회할 수 없습니다."));
    }

}
