package com.sparta.myblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MyblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyblogApplication.class, args);
    }

    // 데이터 저장용 구문
//    @Bean
//    public CommandLineRunner demo(PostRepository postRepository, PostService postService) {
//        return (args) -> {
//            postService.encryptPassword(new PostRequestDto("오늘은 jpa에 대해서 배웠다.", "김현서", "매우 재미있었다.", "1234"));
//            System.out.println("데이터 인쇄");
//        };
//    }
}
