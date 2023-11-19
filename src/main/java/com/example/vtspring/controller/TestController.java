package com.example.vtspring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/name")
    public String getThreadName() {
        log.info("thread name : {}", Thread.currentThread().getName());
        log.info("thread count : {}", Thread.activeCount());
        return Thread.currentThread().getName();
    }
}
