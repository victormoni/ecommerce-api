/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.victormoni.ecommerce.mapper;

import com.victormoni.ecommerce.dto.request.OrderRequest;
import com.victormoni.ecommerce.dto.response.OrderItemResponse;
import com.victormoni.ecommerce.dto.response.OrderResponse;
import com.victormoni.ecommerce.exception.ResourceNotFoundException;
import com.victormoni.ecommerce.model.Order;
import com.victormoni.ecommerce.model.OrderItem;
import com.victormoni.ecommerce.model.OrderStatus;
import com.victormoni.ecommerce.model.Product;
import com.victormoni.ecommerce.repository.ProductRepository;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 *
 * @author Victor Moni
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderMapper {

    private static final DateTimeFormatter fmt = DateTimeFormatter.ISO_DATE_TIME;

    public static Order toEntity(OrderRequest dto, ProductRepository repo) {
        Order order = Order.builder().status(OrderStatus.NEW).build();
        dto.getItems().forEach(i -> {
            Product p = repo.findById(i.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
            order.getItems().add(
                    OrderItem.builder()
                            .order(order)
                            .product(p)
                            .quantity(i.getQuantity())
                            .unitPrice(p.getPrice())
                            .build()
            );
        });
        return order;
    }

    public static OrderResponse toDTO(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .createdAt(order.getCreatedAt().format(fmt))
                .total(order.getTotal())
                .items(order.getItems().stream()
                        .map(OrderItemResponse::fromEntity)
                        .collect(Collectors.<OrderItemResponse>toList())
                )
                .build();
    }
}
