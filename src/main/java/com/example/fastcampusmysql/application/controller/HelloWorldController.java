package com.example.fastcampusmysql.application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class HelloWorldController {
    private final NamedParameterJdbcTemplate jdbcTemplate;


    @GetMapping("/hello")
    public Integer helloWorld() {
        return this.jdbcTemplate.queryForObject("SELECT 1", new MapSqlParameterSource(), Integer.class);
    }
}
