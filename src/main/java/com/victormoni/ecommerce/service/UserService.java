/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.victormoni.ecommerce.service;

import com.victormoni.ecommerce.model.User;
import java.util.Optional;

/**
 *
 * @author Victor Moni
 */
public interface UserService {

    Optional<User> findByUsername(String username);

    User save(User user);

}
