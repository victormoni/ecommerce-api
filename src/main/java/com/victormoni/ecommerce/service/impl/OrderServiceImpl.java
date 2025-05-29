/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.victormoni.ecommerce.service.impl;

import com.victormoni.ecommerce.dto.request.OrderRequest;
import com.victormoni.ecommerce.dto.response.OrderResponse;
import com.victormoni.ecommerce.exception.BusinessException;
import com.victormoni.ecommerce.exception.ResourceNotFoundException;
import com.victormoni.ecommerce.mapper.OrderMapper;
import com.victormoni.ecommerce.model.Order;
import com.victormoni.ecommerce.model.OrderItem;
import com.victormoni.ecommerce.model.Product;
import com.victormoni.ecommerce.repository.OrderRepository;
import com.victormoni.ecommerce.repository.ProductRepository;
import com.victormoni.ecommerce.service.OrderService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author Victor Moni
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public List<OrderResponse> findAll() {
        return orderRepository.findAll().stream()
                .map(OrderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponse findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID " + id));
        return OrderMapper.toDTO(order);
    }

    @Transactional
    public OrderResponse create(OrderRequest dto) {

        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());

        List<OrderItem> items = dto.getItems().stream()
                .map(itemDto -> {
                    Product product = productRepository.findById(itemDto.getProductId())
                            .orElseThrow(() -> new ResourceNotFoundException(
                            "Produto não encontrado com ID " + itemDto.getProductId()));
                    OrderItem item = new OrderItem();
                    item.setOrder(order);
                    item.setProduct(product);
                    item.setProductName(product.getName());
                    item.setQuantity(itemDto.getQuantity());
                    item.setUnitPrice(product.getPrice());
                    return item;
                })
                .collect(Collectors.toList());

        order.getItems().addAll(items);
        order.calculateTotal();

        Order saved = orderRepository.save(order);
        return OrderMapper.toDTO(saved);
    }

    @Transactional
    public OrderResponse update(Long id, OrderRequest dto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID " + id));

        order.getItems().clear();
        List<OrderItem> items = dto.getItems().stream()
                .map(itemDto -> {
                    Product product = productRepository.findById(itemDto.getProductId())
                            .orElseThrow(() -> new ResourceNotFoundException(
                            "Produto não encontrado com ID " + itemDto.getProductId()));

                    if (product.getStock() < itemDto.getQuantity()) {
                        throw new BusinessException("Estoque insuficiente para o produto " + product.getName());
                    }
                    
                    OrderItem item = new OrderItem();
                    item.setOrder(order);
                    item.setProduct(product);
                    item.setProductName(product.getName());
                    item.setQuantity(itemDto.getQuantity());
                    item.setUnitPrice(product.getPrice());
                    return item;
                })
                .collect(Collectors.toList());
        order.getItems().addAll(items);
        order.calculateTotal();

        Order updated = orderRepository.save(order);
        return OrderMapper.toDTO(updated);
    }

    @Transactional
    public void delete(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pedido não encontrado com ID " + id);
        }
        orderRepository.deleteById(id);
    }
}
