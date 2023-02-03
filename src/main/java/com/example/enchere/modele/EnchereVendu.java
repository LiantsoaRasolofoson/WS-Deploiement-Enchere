package com.example.enchere.modele;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "encherevendu")

public class EnchereVendu {

    @Column(name = "idenchere")
    private int idEnchere;

    @Id
    @Column(name = "idoffre")
    private int idOffre;
}
