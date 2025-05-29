package com.victormoni.ecommerce.api;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.victormoni.ecommerce.dto.request.ProductRequest;
import com.victormoni.ecommerce.dto.response.ErrorResponse;
import com.victormoni.ecommerce.dto.response.ProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Victor Moni
 */
@Tag(name = "Produtos", description = "Operações de gerenciamento de produtos")
@RequestMapping("/api/products")
public interface ProductApi {

    @Operation(summary = "Listar todos os produtos", description = "Retorna todos os produtos cadastrados")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Produtos listados com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class))),
        @ApiResponse(responseCode = "401", description = "Não autorizado",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    ResponseEntity<List<ProductResponse>> list();

    @Operation(summary = "Buscar produto por ID", description = "Retorna um produto específico pelo ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Produto encontrado",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class))),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @GetMapping("/{id}")
    ResponseEntity<ProductResponse> findById(@PathVariable Long id);

    @Operation(summary = "Criar novo produto", description = "Adiciona um novo produto")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Produto criado com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @PostMapping
    ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest dto);

    @Operation(summary = "Atualizar produto", description = "Atualiza um produto existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @PutMapping("/{id}")
    ResponseEntity<ProductResponse> update(@PathVariable Long id, @Valid @RequestBody ProductRequest dto);

    @Operation(summary = "Excluir produto", description = "Remove um produto existente")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Produto excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}
