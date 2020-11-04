package com.ksk.project.study_with_me.service;

import com.ksk.project.study_with_me.domain.user.User;
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
    public List<String> findAllNickname() {
        return userRepository.findAll().stream()
                .map(User::getNickname)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponseDto save(UserSignupRequestDto requestDto) {
        return new UserResponseDto(userRepository.save(requestDto.toEntity()));
    }

    @Transactional
    public UserResponseDto changeNickname(Long userCode, String nickname) {
        User entity = userRepository.findById(userCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 User가 없습니다. id=" + userCode));

        entity.changeNickname(nickname);

        return new UserResponseDto(entity);
    }
}
