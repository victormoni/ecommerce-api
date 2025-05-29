package com.victormoni.ecommerce.repository;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.victormoni.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Victor Moni
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

}
