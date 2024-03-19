package com.jfudali.webgameapi.user;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService  {
    void createUser(CreateUserDto createUserDto);

}
