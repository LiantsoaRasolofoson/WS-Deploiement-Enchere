package com.example.enchere.modele;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@Table(name = "offre")

public class Offre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idoffre")
    private int idOffre;

    @Column(name = "idenchere")
    private int idEnchere;

    @Column(name = "idutilisateur")
    private int idUtilisateur;

    @Column(name = "prixoffre")
    private double prixOffre;

    @Column(name = "dateoffre")
    private LocalDateTime dateOffre;

    public void checkUser(V_Enchere enchere)throws Exception{
        if( enchere.getIdUtilisateur() == this.getIdUtilisateur() ){
            throw new RessourceException(new ErrorRetour("Vous ne pouvez pas enchérir sur votre enchère",HttpStatus.BAD_REQUEST.value()));
        }
    }

    public void checkMontant(Offre max)throws Exception{
        if( max.getPrixOffre() >= this.prixOffre ){
            throw new RessourceException(new ErrorRetour("Vous devez entrer un montant supérieur à "+max.getPrixOffre()+" Ar",HttpStatus.BAD_REQUEST.value()));
        }
    }

    public void checkMontant(V_Enchere enchere)throws Exception{
        if( enchere.getPrixEnchere() > this.prixOffre ){
            throw new RessourceException(new ErrorRetour("Vous devez entrer un montant supérieur ou égal à "+enchere.getPrixEnchere()+" Ar",HttpStatus.BAD_REQUEST.value()));
        }
    }
}
