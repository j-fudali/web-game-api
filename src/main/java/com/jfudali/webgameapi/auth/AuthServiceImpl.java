package com.jfudali.webgameapi.auth;

import com.jfudali.webgameapi.config.JwtService;
import com.jfudali.webgameapi.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public AuthenticationResponse login(LoginUserDto loginUserDto) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUserDto.getEmail(),
                                                            loginUserDto.getPassword()));
            var user =
                    userRepository.findByEmail(loginUserDto.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder().token(jwtToken).build();

        }
        catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                                              "Invalid password");
        }
    }

    @Override
    public AuthenticationResponse signUp(CreateUserDto createUserDto) {
        User user = User.builder()
                .username(createUserDto.getUsername())
                .email(createUserDto.getEmail())
                .password(passwordEncoder.encode(createUserDto.getPassword()))
                .role(EUserRole.USER)
                .build();
        userRepository.save(user);
        return AuthenticationResponse.builder().token(jwtService.generateToken(user)).build();
    }
}
