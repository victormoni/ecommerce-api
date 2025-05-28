/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.victormoni.ecommerce.service.impl;

import com.victormoni.ecommerce.dto.request.ProductRequest;
import com.victormoni.ecommerce.dto.response.ProductResponse;
import com.victormoni.ecommerce.exception.ResourceNotFoundException;
import com.victormoni.ecommerce.mapper.ProductMapper;
import com.victormoni.ecommerce.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import com.victormoni.ecommerce.repository.ProductRepository;
import com.victormoni.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
/**
 *
 * @author Victor Moni
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductResponse> getAll() {
        return productRepository.findAll()
                   .stream()
                   .map(ProductMapper::toResponseDTO)
                   .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getById(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado: " + id));
        return ProductMapper.toResponseDTO(product);
    }

    @Override
    public ProductResponse create(ProductRequest productRequestDTO) {
        Product product = ProductMapper.toEntity(productRequestDTO);
        Product saved = productRepository.save(product);
        return ProductMapper.toResponseDTO(saved);
    }

    @Override
    public ProductResponse update(Long id, ProductRequest productRequestDTO) {
        Product existing = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado: " + id));
        ProductMapper.updateEntity(existing, productRequestDTO);
        Product updated = productRepository.save(existing);
        
        return ProductMapper.toResponseDTO(updated);
    }

    @Override
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produto não encontrado: " + id);
        }
        productRepository.deleteById(id);
    }
}
