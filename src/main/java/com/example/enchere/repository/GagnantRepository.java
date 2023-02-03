package com.example.enchere.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.enchere.modele.Gagnant;

public interface GagnantRepository extends JpaRepository<Gagnant, Integer> {
    
    @Query(value = "SELECT * FROM Gagnant WHERE idEnchere = ?1", nativeQuery = true)
    Gagnant getGagnant(int idEnchere);
}
