package com.victormoni.ecommerce.service.impl;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Victor Moni
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> list() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado: " + id));
        return ProductMapper.toResponseDTO(product);
    }

    @Override
    @Transactional
    public ProductResponse create(ProductRequest productRequestDTO) {
        Product product = ProductMapper.toEntity(productRequestDTO);
        Product saved = productRepository.save(product);
        return ProductMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional
    public ProductResponse update(Long id, ProductRequest productRequestDTO) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado: " + id));
        existing.updateFrom(productRequestDTO);
        Product updated = productRepository.save(existing);

        return ProductMapper.toResponseDTO(updated);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produto não encontrado: " + id);
        }
        productRepository.deleteById(id);
    }
}
