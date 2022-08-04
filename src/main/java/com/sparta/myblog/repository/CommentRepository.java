package com.sparta.myblog.repository;

import com.sparta.myblog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<CommentDetailMapping> findCommentByPostId(Long postId);
}
