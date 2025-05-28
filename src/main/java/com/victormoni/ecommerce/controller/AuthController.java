/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.victormoni.ecommerce.controller;

import com.victormoni.ecommerce.api.AuthApi;
import com.victormoni.ecommerce.dto.response.AuthResponse;
import com.victormoni.ecommerce.dto.request.LoginRequest;
import com.victormoni.ecommerce.dto.request.RefreshRequest;
import com.victormoni.ecommerce.dto.request.RegisterRequest;
import com.victormoni.ecommerce.dto.response.SuccessResponse;
import com.victormoni.ecommerce.dto.response.ErrorResponse;
import com.victormoni.ecommerce.model.User;
import com.victormoni.ecommerce.security.JwtUtil;
import com.victormoni.ecommerce.security.CustomUserDetailsService;
import com.victormoni.ecommerce.service.AuthService;
import com.victormoni.ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 *
 * @author Victor Moni
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController implements AuthApi{

    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest body) {
        var auth = authService.authenticate(body.getUsername(), body.getPassword());
        var userDetails = (org.springframework.security.core.userdetails.UserDetails) auth.getPrincipal();
        String access = jwtUtil.generateToken(userDetails);
        String refresh = jwtUtil.generateRefreshToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(access, refresh));
    }

    @Override
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@Valid @RequestBody RefreshRequest body) {

        if (!jwtUtil.isTokenValid(body.getRefreshToken())) {
            return ResponseEntity.status(401).build();
        }
        
        String username = jwtUtil.getUsernameFromToken(body.getRefreshToken());
        var userDetails = userDetailsService.loadUserByUsername(username);
        String newAccess = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(newAccess, body.getRefreshToken()));
    }
    
    @Override
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest body) {

        String username = body.getUsername();
        String password = body.getUsername();

        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Usuário e senha são obrigatórios"));
        }

        if (userService.findByUsername(body.getUsername()).isPresent()) {
            return ResponseEntity.status(409)
                    .body(new ErrorResponse("Usuário já existe"));
        }

        User newUser = new User();
        newUser.setUsername(body.getUsername());
        newUser.setPassword(passwordEncoder.encode(body.getPassword()));
        newUser.setRole(body.getRole());
        userService.save(newUser);
        return ResponseEntity.ok(new SuccessResponse("Usuário registrado com sucesso"));
    }
}
