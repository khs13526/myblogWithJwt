package com.sparta.myblog.model;
import com.sparta.myblog.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
@Getter
@Entity
@NoArgsConstructor
@Table(name = "post")
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String password;

    public Post(PostRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.writer = requestDto.getWriter();
        this.content = requestDto.getContent();
        this.password = requestDto.getPassword();
    }

    public Post(String title, String writer, String content, String password) {
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.password = password;

    }

    public void update(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.writer = requestDto.getWriter();
        this.content = requestDto.getContent();
        this.password = requestDto.getPassword();
    }
}

