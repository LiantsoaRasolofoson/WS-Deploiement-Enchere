package com.example.enchere.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.enchere.modele.Enchere;

public interface EnchereRepository extends JpaRepository<Enchere, Integer> {
    
    @Query(value="SELECT * FROM Enchere WHERE idEnchere = ?1",nativeQuery = true)
    public Enchere getEnchere(int idEnchere);

    @Query(value = "SELECT * FROM Enchere WHERE idUtilisateur = ?1", nativeQuery = true)
    List<Enchere> getAll(int idUtilisateur);

    @Query(value = "SELECT * FROM Enchere WHERE idEnchere NOT IN (SELECT idEnchere FROM EnchereVendu) AND idUtilisateur = ?1", nativeQuery = true)
    List<Enchere> getAllNonTermine(int idUtilisateur);
}

