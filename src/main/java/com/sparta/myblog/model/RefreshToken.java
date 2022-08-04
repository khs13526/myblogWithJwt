package com.sparta.myblog.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Getter
@NoArgsConstructor
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long refreshTokenId;
    @Column
    private String refreshToken;
    @Column
    private String keyUsername;

    public RefreshToken(String refreshToken, String keyUsername){
        this.refreshToken = refreshToken;
        this.keyUsername = keyUsername;
    }
}
