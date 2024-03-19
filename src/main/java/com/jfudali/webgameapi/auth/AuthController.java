package com.jfudali.webgameapi.auth;

import com.jfudali.webgameapi.user.CreateUserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody LoginUserDto loginUserDto){
        return new ResponseEntity<>(this.authService.login(loginUserDto),
                                                          HttpStatus.OK);
    }
    @PostMapping("/sign-up")
    public ResponseEntity<AuthenticationResponse> signUp(@Valid @RequestBody CreateUserDto createUserDto){
        return new ResponseEntity<>(this.authService.signUp(createUserDto),
                                    HttpStatus.OK);
    }
}
