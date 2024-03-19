package com.jfudali.webgameapi.auth;

import com.jfudali.webgameapi.user.CreateUserDto;

public interface AuthService {
    AuthenticationResponse login(LoginUserDto loginUserDto);
    AuthenticationResponse signUp(CreateUserDto createUserDto);
}
