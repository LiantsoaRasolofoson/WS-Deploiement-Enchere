package com.example.enchere.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.enchere.modele.ImageEnchere;

public interface ImageEnchereRepository  extends JpaRepository<ImageEnchere, Integer> {
    
    @Query(value="SELECT * FROM ImageEnchere WHERE idEnchere = ?1", nativeQuery = true)
    List <ImageEnchere> getAll(int idEnchere);
}
