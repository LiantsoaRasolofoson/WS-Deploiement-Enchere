package com.example.enchere.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.enchere.modele.HistoriqueEnchere;

@Repository
public interface HistoriqueEnchereRepository extends MongoRepository<HistoriqueEnchere, String>{
    
    @Query("{'idEnchere' : ?0}")
    List<HistoriqueEnchere> getHistoriques(int idEnchere);
}
