package com.ispan.warashibe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.warashibe.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {

}
