/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.victormoni.ecommerce.controller;

import com.victormoni.ecommerce.api.OrderApi;
import com.victormoni.ecommerce.dto.request.OrderRequest;
import com.victormoni.ecommerce.dto.response.OrderResponse;
import com.victormoni.ecommerce.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Victor Moni
 */
@RestController
@RequestMapping("/api/orders")
@Tag(name = "Orders", description = "Gerenciamento de pedidos")
@RequiredArgsConstructor
public class OrderController implements OrderApi{

    private final OrderService orderService;

    @Operation(summary = "Listar todos os pedidos")
    @GetMapping
    public List<OrderResponse> list() {
        return orderService.findAll();
    }

    @Operation(summary = "Criar um novo pedido")
    @PostMapping
    public ResponseEntity<OrderResponse> create(
            @Valid @RequestBody OrderRequest dto) {

        OrderResponse created = orderService.create(dto);
        URI location = URI.create("/api/orders/" + created.getId());
        return ResponseEntity
                .created(location)
                .body(created);
    }

    @Operation(summary = "Buscar pedido por ID")
    @GetMapping("/{id}")
    public OrderResponse findById(@PathVariable Long id) {
        return orderService.findById(id);
    }

    @Operation(summary = "Atualizar o pedido")
    @PutMapping("/{id}")
    public OrderResponse update(
            @PathVariable Long id,
            @Valid @RequestBody OrderRequest dto) {
        return orderService.update(id, dto);
    }

    @Operation(summary = "Cancelar/excluir pedido")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
