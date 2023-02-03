package com.example.enchere.modele;

import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "HistoriqueEnchere")
public class HistoriqueEnchere {
    
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private String id;
    private Utilisateur utilisateur;
    private int idOffre;
    private int idEnchere;
    private double prixOffre;
    private LocalDateTime dateOffre;


    public void setId(String id){
        this.id = id;
    }

    public void setUtilisateur(Utilisateur utilisateur){
        this.utilisateur = utilisateur;
    }

    public void setIdOffre(int idOffre) {
        this.idOffre = idOffre;
    }

    public void setIdEnchere(int idEnchere) {
        this.idEnchere = idEnchere;
    }

    public void setPrixOffre(double prixOffre) {
        this.prixOffre = prixOffre;
    }

    public void setDateOffre(LocalDateTime dateOffre) {
        System.out.println("Eto "+dateOffre);
        this.dateOffre = dateOffre;
    }
    

    public LocalDateTime getDateOffre() {
        return dateOffre;
    }

    public int getIdOffre() {
        return idOffre;
    }

    public double getPrixOffre() {
        return prixOffre;
    }

    public String getId(){
        return this.id;
    }

    public int getIdEnchere() {
        return idEnchere;
    }

    public Utilisateur getUtilisateur(){
        return this.utilisateur;
    }
    
    public HistoriqueEnchere(){

    }
}
