package com.example.enchere.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.enchere.modele.Commission;

public interface CommissionRepository extends JpaRepository<Commission, Integer> {
    
    @Query(value = "SELECT * FROM Commission ORDER BY dateCommission DESC LIMIT 1", nativeQuery = true)
    public Commission getCommission();

}
