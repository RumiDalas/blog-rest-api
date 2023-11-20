package com.springboot.blog.service;

import com.springboot.blog.payload.RegisterDto;
import com.springboot.blog.payload.loginDto;

public interface AuthService {
    String login(loginDto loginDto );

    String register(RegisterDto registerDto);
}
