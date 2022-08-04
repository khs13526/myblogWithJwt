package com.sparta.myblog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.myblog.apiResponse.ApiResult;
import com.sparta.myblog.apiResponse.ApiUtils;
import com.sparta.myblog.dto.SignupRequestDto;
import com.sparta.myblog.dto.TokenDto;
import com.sparta.myblog.dto.UserRequestDto;
import com.sparta.myblog.model.RefreshToken;
import com.sparta.myblog.repository.RefreshTokenRepository;
import com.sparta.myblog.repository.UserRepository;
import com.sparta.myblog.security.jwt.JwtProvider;
import com.sparta.myblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @PostMapping("/register")
    public ApiResult<?> register(@RequestBody SignupRequestDto requestDto) {
        if(userService.validateUser(requestDto)) {
            if (userService.registerUser(requestDto).equals("200")) {
                return ApiUtils.success(userRepository.findUserByUsername(requestDto.getUsername()));
            } else if (userService.registerUser(requestDto).equals("401")) {
                return ApiUtils.error("중복된 닉네임입니다.", 401);
            } else if (userService.registerUser(requestDto).equals("402")) {
                return ApiUtils.error("비밀번호와 비밀번호 확인이 일치하지 않습니다.", 402);
            }
        }
        return ApiUtils.error("아이디와 비밀번호의 형식을 확인해주세요.", 405);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequestDto requestDto) throws JsonProcessingException {
        if(userService.login(requestDto).equals("200")) {
            TokenDto tokenDto = jwtProvider.createToken(requestDto.getUsername());
            RefreshToken refreshToken = new RefreshToken(tokenDto.getRefreshToken(),requestDto.getUsername());
            refreshTokenRepository.save(refreshToken);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Access-Token", tokenDto.getAccessToken());
            headers.add("Refresh-Token",tokenDto.getRefreshToken());
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(ApiUtils.success(userRepository.findUserByUsername(requestDto.getUsername())));

        } else if (userService.login(requestDto).equals("403")) {
            return ResponseEntity.ok()
                    .body((ApiUtils.error("사용자를 찾을 수 없습니다.", 403)));
        }
        return null;
    }
}
