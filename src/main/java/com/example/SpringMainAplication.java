package com.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class SpringMainAplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMainAplication.class, args); // 애플리케이션 실행
    }
}
