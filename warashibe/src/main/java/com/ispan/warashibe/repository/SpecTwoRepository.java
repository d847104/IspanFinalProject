package com.ispan.warashibe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.warashibe.model.SpecTwos;
import com.ispan.warashibe.model.SpecTwoNames;

public interface SpecTwoRepository extends JpaRepository<SpecTwos, Integer> {
}
