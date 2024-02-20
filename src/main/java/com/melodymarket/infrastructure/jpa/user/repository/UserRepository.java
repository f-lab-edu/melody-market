package com.melodymarket.infrastructure.jpa.user.repository;

import com.melodymarket.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByLoginId(String loginId);

    boolean existsByNickname(String nickname);

    @Override
    <S extends User> S save(S userEntity);

    @Override
    Optional<User> findById(Long id);

    Optional<User> findByLoginId(String loginId);

    @Override
    void deleteById(Long id);
}
