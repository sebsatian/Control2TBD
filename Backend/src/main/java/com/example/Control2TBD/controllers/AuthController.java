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

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
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
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        // Imprimir los datos recibidos en el backend
        System.out.println("Datos recibidos en el backend: " + loginDto);

        try {
            // Autenticar al usuario con los datos proporcionados
            UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(),
                    loginDto.getPassword()
            );
            authenticationManager.authenticate(loginToken);

            // Si la autenticación es exitosa, generar JWT
            String jwt = jwtUtil.create(loginDto.getUsername());

            // Obtener el usuario autenticado de la base de datos
            UserEntity user = userRepository.getByUsername(loginDto.getUsername());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Usuario no encontrado.");
            }

            // Construir la respuesta con el token y la userId
            Map<String, Object> response = new HashMap<>();
            response.put("token", jwt);
            response.put("userId", user.getId());

            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales incorrectas.");
        }
    }




    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto) {
        // Verificar si el usuario ya existe
        UserEntity foundUser = userRepository.getByUsername(registerDto.getUsername());
        if (foundUser != null) { // Usuario ya existe
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario ya existe.");
        }

        try {
            // Crear nuevo usuario
            UserEntity newUser = new UserEntity(
                    null, // ID autogenerado
                    registerDto.getUsername(),
                    passwordEncoder.encode(registerDto.getPassword()) // Encriptar contraseña
            );
            userRepository.saveUser(newUser);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Usuario registrado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar usuario.");
        }
    }
    @PostMapping("/check-token")
    public ResponseEntity<?> checkToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Token inválido.");
            }

            token = token.replace("Bearer ", ""); // Eliminar prefijo "Bearer"
            boolean isValid = jwtUtil.isValid(token); // Usar isValid en lugar de validateToken

            if (!isValid) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Token inválido.");
            }

            return ResponseEntity.ok(Map.of("message", "Token válido"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al validar el token: " + e.getMessage());
        }
    }




}
