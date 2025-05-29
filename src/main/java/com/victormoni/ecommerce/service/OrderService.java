package com.victormoni.ecommerce.service;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
import com.victormoni.ecommerce.dto.request.OrderRequest;
import com.victormoni.ecommerce.dto.response.OrderResponse;
import java.util.List;

/**
 *
 * @author Victor Moni
 */
public interface OrderService {

    List<OrderResponse> findAll();

    OrderResponse findById(Long id);

    OrderResponse create(OrderRequest dto);

    OrderResponse update(Long id, OrderRequest dto);

    void delete(Long id);
}
