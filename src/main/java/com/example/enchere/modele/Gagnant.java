package com.example.enchere.modele;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "Gagnant")
public class Gagnant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idenchere")
    private int idEnchere;

    @Column(name = "idutilisateur")
    private int idUtilisateur;

    @Column(name = "idoffre")
    private int idOffre;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "dateoffre")
    private LocalDateTime dateOffre;

    @Column(name = "prixoffre")
    private double prixOffre;
}
