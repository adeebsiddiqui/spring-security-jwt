package com.ad.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spring-security-jwt")
public class SpringAppController {

    @GetMapping("/greetings")
    public ResponseEntity<String> greet() {
        return ResponseEntity.status(HttpStatus.OK).body("Hello Mr. John Wick!!");
    }
}