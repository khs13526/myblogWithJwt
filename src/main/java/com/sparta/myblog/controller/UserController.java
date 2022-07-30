package com.sparta.myblog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.myblog.apiResponse.ApiResult;
import com.sparta.myblog.apiResponse.ApiUtils;
import com.sparta.myblog.dto.SignupRequestDto;
import com.sparta.myblog.dto.UserRequestDto;
import com.sparta.myblog.model.User;
import com.sparta.myblog.repository.UserRepository;
import com.sparta.myblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping("/auth/register")
    public ApiResult<Boolean> register(@RequestBody SignupRequestDto requestDto) {
        
        return ApiUtils.success(userService.registerUser(requestDto));
    }

    @PostMapping("/auth/login")
    public ApiResult<Map<String, String>> login(@RequestBody UserRequestDto requestDto) throws JsonProcessingException {
        User user = userService.login(requestDto);
        return ApiUtils.success(userService.createToken(user));
    }

}
