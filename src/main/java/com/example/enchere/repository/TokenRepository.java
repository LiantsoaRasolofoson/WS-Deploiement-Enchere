package com.example.enchere.repository;

import com.example.enchere.modele.Token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query(value="SELECT * FROM Token t WHERE t.dateExpiration > now() AND t.idutilisateur = ?1 ORDER BY t.dateExpiration DESC LIMIT 1", nativeQuery =true)
    public Token getTokenByUserNotExpired(int idUtilisateur);

    @Query(value="SELECT * FROM Token t WHERE t.tokenValues = ?1", nativeQuery =true)
    public Token getToken(String tokenValues);

}
