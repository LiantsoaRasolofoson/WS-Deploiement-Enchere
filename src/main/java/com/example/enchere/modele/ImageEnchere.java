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
@Table(name = "imageenchere")

public class ImageEnchere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idimageenchere")
    private int idImageEnchere;

    @Column(name = "nomimage")
    private String nomImage;

    @Column(name = "format")
    private String format;

    @Column(name = "idenchere")
    private int idEnchere;
}
