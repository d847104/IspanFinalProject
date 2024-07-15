package com.ispan.warashibe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.warashibe.model.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {

}
