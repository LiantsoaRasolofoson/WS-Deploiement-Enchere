package com.example.enchere.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.enchere.modele.RechargementValide;

public interface RechargementValideRepository extends JpaRepository<RechargementValide, Integer> {
    
}
