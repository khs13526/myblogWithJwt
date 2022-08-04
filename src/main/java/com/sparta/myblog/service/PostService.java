package com.sparta.myblog.service;
import com.sparta.myblog.dto.PostResponseDto;
import com.sparta.myblog.model.Comment;
import com.sparta.myblog.model.Post;
import com.sparta.myblog.repository.CommentDetailMapping;
import com.sparta.myblog.repository.CommentRepository;
import com.sparta.myblog.repository.PostDetailMapping;
import com.sparta.myblog.repository.PostRepository;
import com.sparta.myblog.dto.PostRequestDto;
import com.sparta.myblog.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final JwtProvider jwtProvider;

    @Transactional // SQL 쿼리가 일어나야 함을 스프링에게 알려줌
    public List<PostDetailMapping> update(Long id, PostRequestDto requestDto, String writer) {
        Post post1 = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다.")
        );
        if(post1.getWriter().equals(writer)){
            post1.update(requestDto);
            return postRepository.findPostDetailById(id);
        } else {
            return null;
        }

    }

    public Long save(PostRequestDto requestDto, String token) {
            String writer = jwtProvider.getUserPk(token);
            Post post = new Post(requestDto, writer);
            postRepository.save(post);
            return post.getId();
        }

    public PostResponseDto post(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다")
        );
        List<CommentDetailMapping> commentList = commentRepository.findCommentByPostId(id);
        return new PostResponseDto(post,commentList);
    }

    public boolean delete(Long id, String writer) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );
        if(post.getWriter().equals(writer)){
            postRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}
