package com.melodymarket.infrastructure.repository;

import com.melodymarket.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByLoginId(String loginId);

    boolean existsByNickname(String nickname);

    @Override
    <S extends UserEntity> S save(S userEntity);

    @Override
    Optional<UserEntity> findById(Long id);

    Optional<UserEntity> findByLoginId(String loginId);

    @Override
    void deleteById(Long id);
}
