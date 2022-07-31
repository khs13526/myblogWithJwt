package com.sparta.myblog.repository;

import com.sparta.myblog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    UserDetails findUserDetailsByUsername(String username);

    List<UserDetailMapping> findUserByUsername(String username);
}
