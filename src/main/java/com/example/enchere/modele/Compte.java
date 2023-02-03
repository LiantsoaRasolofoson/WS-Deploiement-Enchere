package com.example.enchere.modele;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

import javax.persistence.*;

import org.springframework.http.HttpStatus;

import com.example.enchere.exeption.RessourceException;
import com.example.enchere.retour.ErrorRetour;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "compte")

public class Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcompte")
    private int idCompte;

    @Column(name = "idutilisateur")
    private int idUtilisateur;

    @Column(name = "numero")
    private String numero;

    @Column(name = "datecompte")
    private Date date;

    @Column(name = "solde")
    private double solde;

    public void checkSolde(double montant)throws Exception{
        if( this.solde < montant ){
            throw new RessourceException(new ErrorRetour("Votre solde est insuffisant! Vous devez recharger votre compte",HttpStatus.BAD_REQUEST.value()));
        }
        else{
            this.setSolde(solde - montant);
        }
    }

}
