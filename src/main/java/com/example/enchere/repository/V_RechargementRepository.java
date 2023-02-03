package com.example.enchere.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.enchere.modele.V_Rechargement;

public interface V_RechargementRepository extends JpaRepository<V_Rechargement, Integer>{
    
    @Query(value = "SELECT * FROM V_Rechargement WHERE idRechargement NOT IN (SELECT idRechargement FROM RechargementValide)", nativeQuery = true)
    public List<V_Rechargement> nonValides();

    @Query(value = "SELECT * FROM V_Rechargement WHERE idRechargement IN (SELECT idRechargement FROM RechargementValide)", nativeQuery = true)
    public List<V_Rechargement> valides();
}
