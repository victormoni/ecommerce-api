/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.victormoni.ecommerce.repository;

import com.victormoni.ecommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Victor Moni
 */
public interface OrderRepository extends JpaRepository<Order,Long>{

}
