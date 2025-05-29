package com.victormoni.ecommerce.controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.victormoni.ecommerce.api.OrderApi;
import com.victormoni.ecommerce.dto.request.OrderRequest;
import com.victormoni.ecommerce.dto.response.OrderResponse;
import com.victormoni.ecommerce.model.OrderStatus;
import com.victormoni.ecommerce.service.OrderService;
import com.victormoni.ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Victor Moni
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
@Tag(name = "Pedidos", description = "Operações de gerenciamento de pedidos")
public class OrderController implements OrderApi{

    private final OrderService orderService;
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderResponse> list() {
        return orderService.list();
    }

    @GetMapping("/me")
    public ResponseEntity<Page<OrderResponse>> findMyOrders(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<OrderResponse> orders = orderService.findByUser(userDetails.getUsername(), pageable);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public OrderResponse findById(@PathVariable Long id) {
        return orderService.findById(id);
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<OrderResponse>> findByStatus(@PathVariable OrderStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<OrderResponse> orders = orderService.findByStatus(status, pageable);
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody OrderRequest request) {

        String username = userDetails.getUsername();
        OrderResponse response = orderService.create(username, request);
        return ResponseEntity.ok(response);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> update(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id,
            @Valid @RequestBody OrderRequest request) {

        String username = userDetails.getUsername();
        OrderResponse response = orderService.update(username, id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
