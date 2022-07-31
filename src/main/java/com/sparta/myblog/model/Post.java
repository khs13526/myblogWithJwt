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


    public Post(PostRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.writer = requestDto.getWriter();
        this.content = requestDto.getContent();
    }

    public Post(String title, String writer, String content) {
        this.title = title;
        this.writer = writer;
        this.content = content;

    }

    public void update(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.writer = requestDto.getWriter();
        this.content = requestDto.getContent();
    }
}

