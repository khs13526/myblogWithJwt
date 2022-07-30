package com.sparta.myblog.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.myblog.dto.SignupRequestDto;
import com.sparta.myblog.dto.TokenDto;
import com.sparta.myblog.dto.UserRequestDto;
import com.sparta.myblog.model.User;
import com.sparta.myblog.repository.UserRepository;
import com.sparta.myblog.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;

    public boolean registerUser(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        if(userRepository.findByUsername(username).isPresent()){
            throw new IllegalArgumentException("중복된 닉네임입니다");
        }

        String encryptedPassword = bCryptPasswordEncoder.encode(signupRequestDto.getPassword());
        User user = new User(signupRequestDto.getUsername(), encryptedPassword);
        userRepository.save(user);
        return true;
    }

    public User login(UserRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("닉네임을 찾을 수 없습니다."));
        if(!bCryptPasswordEncoder.matches(requestDto.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 다릅니다.");
        }
        return user;
    }

    public Map<String, String> createToken(User user) {
        TokenDto tokenDto = jwtProvider.createToken(user);
        Map<String, String> result = new HashMap<>();
        result.put("accessToken", tokenDto.getAccessToken());
        result.put("refreshToken", tokenDto.getRefreshToken());
        result.put("userId", user.getUsername());
        return result;
    }

    public UserDetails loadUserByUsername(String username) {
        return userRepository.findUserDetailsByUsername(username);
    }
}
