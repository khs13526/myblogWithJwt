package com.sparta.myblog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sparta.myblog.apiResponse.ApiResult;
import com.sparta.myblog.apiResponse.ApiUtils;
import com.sparta.myblog.dto.PostRequestDto;
import com.sparta.myblog.dto.PostResponseDto;
import com.sparta.myblog.repository.PostDetailMapping;
import com.sparta.myblog.repository.PostRepository;
import com.sparta.myblog.security.jwt.JwtProvider;
import com.sparta.myblog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostController {

    private final PostRepository postRepository;
    private final PostService postService;

    private final JwtProvider jwtProvider;

    @PostMapping("/auth/post")
    public ApiResult<List<PostDetailMapping>> createPost(@RequestHeader("Authorization") String token, @RequestBody PostRequestDto requestDto) throws Exception {
        return ApiUtils.success(postRepository.findPostDetailById(postService.save(requestDto, token)));
    }

    @GetMapping("/post")
    public ApiResult<List<PostDetailMapping>> getPosts() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        log.info(mapper.writeValueAsString(postRepository.findAllBy(Sort.by(Sort.Direction.DESC, "createdAt"))));
        return ApiUtils.success(postRepository.findAllBy(Sort.by(Sort.Direction.DESC, "createdAt")));
    }

    @GetMapping("/post/{id}")
    public ApiResult<PostResponseDto> getPostById(@PathVariable Long id) {
        return ApiUtils.success(postService.post(id));
    }

    @PutMapping("/auth/post/{id}")
    public ApiResult<?> updatePost(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody PostRequestDto requestDto) throws Exception {
        String writer = jwtProvider.getUserPk(token);
        return ApiUtils.success(postService.update(id, requestDto, writer));
    }

    @DeleteMapping("/auth/post/{id}")
    public ApiResult<?> deletePost(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        String writer = jwtProvider.getUserPk(token);
        if(postService.delete(id, writer)){
            return ApiUtils.success(true);
        } else {
            return ApiUtils.error("작성한 게시글이 아닙니다.", 406);
        }
    }
}
