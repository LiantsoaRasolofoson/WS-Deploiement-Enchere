package com.example.enchere.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.enchere.modele.Offre;
import com.example.enchere.modele.Utilisateur;

public interface OffreRepository extends JpaRepository<Offre, Integer> {

    @Query(value = "SELECT * FROM Offre WHERE idEnchere = ?1 ORDER BY prixOffre DESC LIMIT 1", nativeQuery = true)
    public Offre getOffreMax(int idEnchere);

    @Query(value = "SELECT * FROM Utilisateur WHERE idUtilisateur IN (SELECT idUtilisateur FROM Offre WHERE idEnchere = ?1)", nativeQuery = true)
    List<Utilisateur> getAllOffre(int idEnchere);
}