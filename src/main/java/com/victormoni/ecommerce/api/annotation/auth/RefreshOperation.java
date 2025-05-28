/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.victormoni.ecommerce.api.annotation.auth;

import com.victormoni.ecommerce.dto.response.AuthResponse;
import com.victormoni.ecommerce.dto.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Victor Moni
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
  summary     = "Refresh Token",
  description = "Renova o access token a partir do refresh token"
)
@ApiResponses({
  @ApiResponse(
    responseCode = "200",
    description  = "Token renovado com sucesso",
    content = @Content(
      mediaType = "application/json",
      schema    = @Schema(implementation = AuthResponse.class),
      examples  = @ExampleObject(
        name  = "OK",
        value = "{\"accessToken\":\"eyJ…\",\"refreshToken\":\"eyJ…\"}"
      )
    )
  ),
  @ApiResponse(
    responseCode = "400",
    description  = "Refresh token não informado ou inválido",
    content = @Content(
      mediaType = "application/json",
      schema    = @Schema(implementation = ErrorResponse.class),
      examples  = @ExampleObject(
        name  = "Bad Request",
        value = "{\"error\":\"Refresh token é obrigatório\"}"
      )
    )
  ),
  @ApiResponse(
    responseCode = "401",
    description  = "Refresh token expirado ou inválido",
    content = @Content(
      mediaType = "application/json",
      schema    = @Schema(implementation = ErrorResponse.class),
      examples  = @ExampleObject(
        name  = "Unauthorized",
        value = "{\"error\":\"Refresh token expirado ou inválido\"}"
      )
    )
  )
})
public @interface RefreshOperation {}