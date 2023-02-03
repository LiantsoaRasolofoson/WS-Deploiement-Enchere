package com.example.enchere.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.enchere.modele.Admins;

public interface AdminsRepository extends JpaRepository<Admins, Integer> {

    @Query(value="SELECT * FROM Admins a WHERE a.Email = ?1 AND a.motDePasse = ?2", nativeQuery =true)
    public Admins login(String email, String motDePasse);

    @Query(value="SELECT * FROM Admins a WHERE a.nom = ?1 ", nativeQuery =true)
    public Admins getNomAdmins(String nom);

}
