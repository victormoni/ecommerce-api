/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.victormoni.ecommerce.api.annotation.auth;

import com.victormoni.ecommerce.dto.response.ErrorResponse;
import com.victormoni.ecommerce.dto.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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
  summary     = "Register",
  description = "Registra um novo usuário"
)
@ApiResponses({
  @ApiResponse(
    responseCode = "200",
    description = "Usuário registrado com sucesso",
    content = @Content(
      mediaType = "application/json",
      schema    = @Schema(implementation = SuccessResponse.class)
    )
  ),
  @ApiResponse(
    responseCode = "400",
    description = "Dados de registro inválidos",
    content = @Content(
      mediaType = "application/json",
      schema    = @Schema(implementation = ErrorResponse.class)
    )
  ),
  @ApiResponse(
    responseCode = "409",
    description = "Usuário já existe",
    content = @Content(
      mediaType = "application/json",
      schema    = @Schema(implementation = ErrorResponse.class)
    )
  )
})
public @interface RegisterOperation {
}
