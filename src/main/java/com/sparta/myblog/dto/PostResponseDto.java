package com.sparta.myblog.dto;

import com.sparta.myblog.model.Post;
import com.sparta.myblog.repository.CommentDetailMapping;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private List<CommentDetailMapping> commentList;

    public PostResponseDto(Post post,List<CommentDetailMapping> commentList){
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.writer = post.getWriter();
        this.commentList = commentList;
    }
}
