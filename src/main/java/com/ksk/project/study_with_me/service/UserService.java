package com.ksk.project.study_with_me.service;

import com.ksk.project.study_with_me.domain.user.UserRepository;
import com.ksk.project.study_with_me.web.dto.user.UserResponseDto;
import com.ksk.project.study_with_me.web.dto.user.UserSignupRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAllNickname() {
        return userRepository.findAll().stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponseDto save(UserSignupRequestDto requestDto) {
        return new UserResponseDto(userRepository.save(requestDto.toEntity()));
    }
}
