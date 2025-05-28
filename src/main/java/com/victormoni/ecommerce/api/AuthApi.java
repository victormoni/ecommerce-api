/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.victormoni.ecommerce.api;

import com.victormoni.ecommerce.api.annotation.auth.LoginOperation;
import com.victormoni.ecommerce.api.annotation.auth.RefreshOperation;
import com.victormoni.ecommerce.api.annotation.auth.RegisterOperation;
import com.victormoni.ecommerce.dto.request.LoginRequest;
import com.victormoni.ecommerce.dto.request.RefreshRequest;
import com.victormoni.ecommerce.dto.request.RegisterRequest;
import com.victormoni.ecommerce.dto.response.AuthResponse;
import com.victormoni.ecommerce.dto.response.SuccessResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Victor Moni
 */
@Tag(name = "Auth", description = "Endpoints de autenticação")
public interface AuthApi {

    @LoginOperation
    ResponseEntity<AuthResponse> login(@Valid LoginRequest body);

    @RefreshOperation
    ResponseEntity<AuthResponse> refresh(@Valid RefreshRequest body);

    @RegisterOperation
    ResponseEntity<?> register(@Valid RegisterRequest body);
}
