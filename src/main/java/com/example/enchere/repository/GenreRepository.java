package com.example.enchere.repository;

import com.example.enchere.modele.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer>{
    
}
