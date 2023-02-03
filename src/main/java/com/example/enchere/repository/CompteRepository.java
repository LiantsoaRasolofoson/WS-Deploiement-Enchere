package com.example.enchere.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.enchere.modele.Compte;

public interface CompteRepository extends JpaRepository<Compte, Integer> {
    
    @Query(value="SELECT * FROM Compte WHERE idUtilisateur = ?1", nativeQuery = true)
    public Compte getCompte(int idUtilisateur);

}
