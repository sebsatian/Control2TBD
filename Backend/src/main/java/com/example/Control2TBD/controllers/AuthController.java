package com.example.Control2TBD.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.Control2TBD.config.JwtUtil;
import com.example.Control2TBD.persistence.dto.LoginDto;
import com.example.Control2TBD.persistence.dto.RegisterDto;
import com.example.Control2TBD.persistence.entities.UserEntity;
import com.example.Control2TBD.persistence.repositories.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginDto loginDto) {
        try { // Intentar autenticar al user
            UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(),
                    loginDto.getPassword());
            authenticationManager.authenticate(login);

            // Si la autenticación fue exitosa, crear un JWT y devolverlo en el header
            String jwt = this.jwtUtil.create(loginDto.getUsername());

            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt).build();
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterDto registerDto) {
        // SEARCH IF USER EXISTS
        UserEntity foundUser = userRepository.getByUsername(registerDto.getUsername());
        if (foundUser == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        //USER DOES NOT EXIST, SO CREATE NEW ONE
        UserEntity newUser = new UserEntity(
                null,
                registerDto.getUsername(),
                passwordEncoder.encode(registerDto.getPassword())
        );
        userRepository.saveUser(newUser);

        return ResponseEntity.ok().build();
    }
}