package com.sparta.myblog.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CommentRequestDto {
    private final Long postId;
    private final String content;
    private final String writer;
}
