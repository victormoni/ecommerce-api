/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.victormoni.ecommerce.api.annotation.order;

import com.victormoni.ecommerce.dto.response.ErrorResponse;
import com.victormoni.ecommerce.dto.response.OrderResponse;
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
@Operation(summary = "Atualizar pedido",
        description = "Atualiza um pedido existente pelo ID")
@ApiResponses({
    @ApiResponse(responseCode = "200",
            description = "Pedido atualizado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OrderResponse.class)
            )
    ),
    @ApiResponse(responseCode = "400",
            description = "Dados inválidos",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
            )
    ),
    @ApiResponse(responseCode = "404",
            description = "Pedido não encontrado",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
            )
    ),
    @ApiResponse(responseCode = "401",
            description = "Não autorizado")
})
public @interface UpdateOperation {
}
