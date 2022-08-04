package com.sparta.myblog.repository;

import java.time.LocalDateTime;

public interface CommentDetailMapping {

    String getPostId();
    String getWriter();
    String getContent();
    LocalDateTime getCreatedAt();
    LocalDateTime getModifiedAt();

}
