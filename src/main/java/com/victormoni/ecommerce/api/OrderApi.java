/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.victormoni.ecommerce.api;

import com.victormoni.ecommerce.dto.request.OrderRequest;
import com.victormoni.ecommerce.dto.response.OrderResponse;
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
import com.victormoni.ecommerce.api.annotation.order.CreateOperation;
import com.victormoni.ecommerce.api.annotation.order.DeleteOperation;
import com.victormoni.ecommerce.api.annotation.order.GetOperation;
import com.victormoni.ecommerce.api.annotation.order.ListOperation;
import com.victormoni.ecommerce.api.annotation.order.UpdateOperation;

/**
 *
 * @author Victor Moni
 */
@Tag(name = "Orders", description = "Gerenciamento de pedidos")
@RequestMapping("/api/orders")
public interface OrderApi {

    @ListOperation
    @GetMapping
    List<OrderResponse> list();

    @CreateOperation
    @PostMapping
    ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderRequest dto);

    @GetOperation
    @GetMapping("/{id}")
    OrderResponse get(@PathVariable Long id);

    @UpdateOperation
    @PutMapping("/{id}")
    OrderResponse update(@PathVariable Long id,
            @Valid @RequestBody OrderRequest dto);

    @DeleteOperation
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}
