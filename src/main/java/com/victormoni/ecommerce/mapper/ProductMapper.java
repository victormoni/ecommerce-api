/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.victormoni.ecommerce.mapper;

import com.victormoni.ecommerce.dto.request.ProductRequest;
import com.victormoni.ecommerce.dto.response.ProductResponse;
import com.victormoni.ecommerce.model.Product;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Victor Moni
 */
public class ProductMapper {

    private static final DateTimeFormatter FMT = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Product toEntity(ProductRequest dto) {
        if (dto == null) return null;
        return Product.builder()
            .name(dto.getName())
            .description(dto.getDescription())
            .price(dto.getPrice())
            .stock(dto.getStock())
            .build();
    }

    public static ProductResponse toResponseDTO(Product p) {
        if (p == null) return null;
        return ProductResponse.builder()
            .id(p.getId())
            .name(p.getName())
            .description(p.getDescription())
            .price(p.getPrice())
            .stock(p.getStock())
            .createdAt(p.getCreatedAt() == null
               ? null
               : p.getCreatedAt().format(FMT))
            .build();
    }

    public static void updateEntity(Product p, ProductRequest dto) {
        if (p == null || dto == null) return;
        p.setName(dto.getName());
        p.setDescription(dto.getDescription());
        p.setPrice(dto.getPrice());
        p.setStock(dto.getStock());
    }
}
