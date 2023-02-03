package com.example.enchere.modele;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Date;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rechargement")

public class Rechargement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrechargement")
    private int idRechargement;

    @Column(name = "idcompte")
    private int idCompte;

    @Column(name = "montant")
    private double montant;

    @Column(name = "daterechargement")
    private Date dateRechargement;



}
