package com.sparta.myblog.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.myblog.dto.SignupRequestDto;
import com.sparta.myblog.dto.TokenDto;
import com.sparta.myblog.dto.UserRequestDto;
import com.sparta.myblog.model.User;
import com.sparta.myblog.repository.UserRepository;
import com.sparta.myblog.security.jwt.JwtProvider;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;


    public String registerUser(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        if(userRepository.findByUsername(username).isPresent()){
            return "401";
        }
        if(!signupRequestDto.getPassword().equals(signupRequestDto.getPasswordCheck())) {
            return "402";
        }

        String encryptedPassword = bCryptPasswordEncoder.encode(signupRequestDto.getPassword());
        User user = new User(signupRequestDto.getUsername(), encryptedPassword);
        userRepository.save(user);
        return "200";
    }

    public String login(UserRequestDto requestDto) {
        Optional<User> user = userRepository.findByUsername(requestDto.getUsername());
        if(user.isEmpty()){
            return "403";
        } else {
            if(!bCryptPasswordEncoder.matches(requestDto.getPassword(), user.get().getPassword())){
                return "403";
            } else {
                return "200";
            }
        }
    }

    public Map<String, String> createToken(User user) {
        TokenDto tokenDto = jwtProvider.createToken(user);
        Map<String, String> result = new HashMap<>();
        result.put("accessToken", tokenDto.getAccessToken());
        result.put("refreshToken", tokenDto.getRefreshToken());
        result.put("userId", user.getUsername());
        return result;
    }

    public boolean validateUser(SignupRequestDto signupRequestDto) {
        return Pattern.matches("^[A-Za-z0-9]{4,12}$", signupRequestDto.getUsername()) && Pattern.matches("^[A-Za-z0-9]{4,32}$", signupRequestDto.getPassword());
    }


}
