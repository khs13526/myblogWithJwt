package com.sparta.myblog.controller;

import com.sparta.myblog.apiResponse.ApiResult;
import com.sparta.myblog.apiResponse.ApiUtils;
import com.sparta.myblog.dto.CommentRequestDto;
import com.sparta.myblog.repository.CommentRepository;
import com.sparta.myblog.security.jwt.JwtProvider;
import com.sparta.myblog.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentController {

    private final JwtProvider jwtProvider;

    private final CommentRepository commentRepository;

    private final CommentService commentService;

    @PostMapping("/auth/comment")
    public ApiResult<?> createComment (@RequestHeader("Authorization") String token, @RequestBody CommentRequestDto requestDto){
        log.info(token);
        String writer = jwtProvider.getUserPk(token);
        return ApiUtils.success(commentRepository.findById(commentService.save(requestDto,writer)));
    }
    @PutMapping("/auth/comment/{id}")
    public ApiResult<?> updateComment(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody CommentRequestDto requestDto) throws Exception {
        String writer = jwtProvider.getUserPk(token);
        if(commentService.update(id, requestDto, writer).isPresent()){
            return ApiUtils.success(commentService.update(id, requestDto, writer));
        } else {
            return ApiUtils.error("작성한 댓글이 아닙니다.", 406);
        }
    }
    @DeleteMapping("/auth/comment/{id}")
    public ApiResult<?> deleteComment(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        String writer = jwtProvider.getUserPk(token);
        if(commentService.delete(id, writer)){
            return ApiUtils.success(true);
        } else {
            return ApiUtils.error("작성한 댓글이 아닙니다.", 406);
        }
    }

}

