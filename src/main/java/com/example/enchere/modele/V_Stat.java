package com.example.enchere.modele;


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
@Table(name = "v_stat")

public class V_Stat {
    @Id
    @Column(name = "idcategorie")
    private int idCategorie;

    @Column(name = "categorie")
    private String categorie;

    @Column(name = "count")
    private double count;

}
