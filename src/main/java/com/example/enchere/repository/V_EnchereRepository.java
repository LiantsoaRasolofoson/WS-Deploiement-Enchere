package com.example.enchere.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.enchere.modele.V_Enchere;

@Repository
public interface V_EnchereRepository extends JpaRepository<V_Enchere, Integer> {
    
    @Query(value = "SELECT * FROM V_Enchere WHERE idUtilisateur = ?1", nativeQuery = true)
    List<V_Enchere> getAll(int idUtilisateur);

    @Query(value = "SELECT * FROM V_Enchere WHERE idEnchere = ?1", nativeQuery = true)
    V_Enchere getEnchere(int idEnchere);

    @Query(value = "SELECT * FROM V_Enchere WHERE statusEnchere = 'Termine'", nativeQuery = true)
    List<V_Enchere> getAllTermine();

    @Query(value = "SELECT * FROM V_Enchere WHERE statusEnchere = 'En Cours'", nativeQuery = true)
    List<V_Enchere> getAllEnCours();

    @Query(value = "SELECT * FROM V_Enchere WHERE statusEnchere = 'Non Demarre'", nativeQuery = true)
    List<V_Enchere> getAllNonDemarrer();

   
}