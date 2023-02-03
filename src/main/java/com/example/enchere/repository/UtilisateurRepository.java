package com.example.enchere.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.enchere.modele.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    
    @Query(value = "SELECT * FROM Utilisateur WHERE Utilisateur.email = ?1 AND Utilisateur.motDePasse = ?2", nativeQuery = true)
    public Utilisateur login(String email, String motDePasse);

}