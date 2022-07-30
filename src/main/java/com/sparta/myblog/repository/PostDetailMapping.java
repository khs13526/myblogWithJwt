package com.sparta.myblog.repository;
import java.time.LocalDateTime;

public interface PostDetailMapping {
    Long getId();
    String getTitle();
    String getWriter();
    String getContent();
    LocalDateTime getCreatedAt();
    LocalDateTime getModifiedAt();
}
