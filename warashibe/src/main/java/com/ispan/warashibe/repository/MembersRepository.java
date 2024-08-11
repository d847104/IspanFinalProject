package com.ispan.warashibe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.warashibe.model.Members;

public interface MembersRepository extends JpaRepository<Members, Integer> {

	
//    @Query(value="SELECT * FROM Members m WHERE name=:name")
//    Members findByUsername(String username);
    
    Members findByAccount(String account);
    
    
}
