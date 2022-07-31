package com.sparta.myblog.repository;

import java.time.LocalDateTime;

public interface UserDetailMapping {
    Long getId();
    String getUsername();
    LocalDateTime getCreatedAt();
    LocalDateTime getModifiedAt();
}
