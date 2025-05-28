/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.victormoni.ecommerce.dto.response;

import com.victormoni.ecommerce.model.OrderItem;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Victor Moni
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponse {
    private Long    productId;
    private String  productName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal total;

    public static OrderItemResponse fromEntity(OrderItem item) {
        BigDecimal qty = BigDecimal.valueOf(item.getQuantity());
        BigDecimal tot = item.getUnitPrice().multiply(qty);

        return OrderItemResponse.builder()
            .productId(   item.getProduct().getId())
            .productName( item.getProduct().getName())
            .quantity(    item.getQuantity())
            .unitPrice(   item.getUnitPrice())
            .total(       tot)
            .build();
    }
}