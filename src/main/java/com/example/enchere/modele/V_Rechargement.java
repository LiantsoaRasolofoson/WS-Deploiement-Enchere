package com.example.enchere.modele;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "v_rechargement")

public class V_Rechargement {
    @Id
    @Column(name = "idrechargement")
    private int idRechargement;

    @Column(name = "idcompte")
    private int idCompte;

    @Column(name = "montant")
    private double montant;

    @Column(name = "daterechargement")
    private Date dateRechargement;

    @Column(name = "numero")
    private String numero;
}
