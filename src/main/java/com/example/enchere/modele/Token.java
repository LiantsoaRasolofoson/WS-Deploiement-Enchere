package com.example.enchere.modele;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.time.LocalDateTime;

import javax.persistence.*;

import org.springframework.http.HttpStatus;

import com.example.enchere.exeption.RessourceException;
import com.example.enchere.retour.ErrorRetour;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "token")
public class Token {
    
    @Id
    @Column(name = "idtoken")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idToken;

    @Column(name = "idutilisateur")
    private int idUtilisateur;

    @Column(name = "tokenvalues")
    private String tokenValues;

    @Column( name = "dateexpiration")
    private LocalDateTime dateExpiration;


    public Token generateToken(Utilisateur user, int hour)throws Exception{
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        String token = user.getEmail()+user.getMotDePasse()+LocalDateTime.now();
        byte [] m = md.digest(token.getBytes());
        BigInteger bi = new BigInteger(1,m);
        String hash = bi.toString(16);
        while(  hash.length() < 32 ){
            hash = "0"+hash;
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime d = now.plusHours(hour);
        Token t = new Token();
        t.setIdUtilisateur(user.getIdUtilisateur());
        t.setTokenValues(hash);
        t.setDateExpiration(d);
        return t;
    }

    public void bearerToken(Token token)throws Exception{
        if( token.getDateExpiration().isBefore(LocalDateTime.now()) ){
            throw new RessourceException(new ErrorRetour("Token expiré",HttpStatus.BAD_REQUEST.value()));
        }
    }
}
