package com.sparta.myblog.model;

import com.sparta.myblog.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String writer;



    public Comment(Long postId, String content) {
        this.postId = postId;
        this.content = content;
    }

    public Comment(CommentRequestDto requestDto, String writer){
        this.postId = requestDto.getPostId();
        this.content = requestDto.getContent();
        this.writer = writer;
    }

    public void update(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();
    }
}
