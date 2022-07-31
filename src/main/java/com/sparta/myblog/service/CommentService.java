package com.sparta.myblog.service;

import com.sparta.myblog.dto.CommentRequestDto;
import com.sparta.myblog.dto.PostRequestDto;
import com.sparta.myblog.model.Comment;
import com.sparta.myblog.model.Post;
import com.sparta.myblog.repository.CommentRepository;
import com.sparta.myblog.repository.PostDetailMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public Long save(CommentRequestDto requestDto, String writer) {
        Comment comment = new Comment(requestDto, writer);
        commentRepository.save(comment);
        return comment.getId();
    }

    @Transactional // SQL 쿼리가 일어나야 함을 스프링에게 알려줌
    public Optional<Comment> update(Long id, CommentRequestDto requestDto, String writer) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );
        if(comment.getWriter().equals(writer)){
            comment.update(requestDto);
            return commentRepository.findById(id);
        } else {
            return Optional.empty();
        }
    }

    public boolean delete(Long id, String writer) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );
        if(comment.getWriter().equals(writer)){
            commentRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
