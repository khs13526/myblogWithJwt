package com.sparta.myblog.repository;

import com.sparta.myblog.model.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<PostDetailMapping> findAllBy(Sort createdAt);
    List<PostDetailMapping> findPostDetailById(Long id);
}
