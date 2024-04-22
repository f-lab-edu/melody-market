package com.melodymarket.application.theater.service;

import com.melodymarket.infrastructure.jpa.theater.repository.TheaterRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class TheaterAuthorizationServiceImpl implements TheaterAuthorizationService {
    TheaterRepository theaterRepository;

    @Override
    public void authenticationUserId(Long userId, Long theaterId) {
        Optional<Long> foundUserId = theaterRepository.findUserIdById(theaterId);
        if (foundUserId.isEmpty() || !foundUserId.get().equals(userId)) {
            throw new IllegalStateException("유저(" + userId + ") 는 (" + theaterId + ") 에 대한 작업 권한이 불충분 합니다.");
        }
    }
}
