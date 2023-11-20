package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.RegisterDto;
import com.springboot.blog.payload.loginDto;
import com.springboot.blog.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private AuthService authService ;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

//    Build login rest api

@PostMapping(value = {"/login","/signin"})
public ResponseEntity<String> login(@RequestBody loginDto loginDto){
    String response = authService.login(loginDto);
    return ResponseEntity.ok(response);
}

//    Build register rest api
@PostMapping(value = {"/register","/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response , HttpStatus.CREATED);
    }

}
