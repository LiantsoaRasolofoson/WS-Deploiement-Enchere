package com.example.enchere.modele;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.sql.Time;
import java.sql.Timestamp;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "enchere")

public class Enchere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idenchere")
    private int idEnchere;

    @Column(name = "nom")
    private String nom;

    @Column(name = "descriptions")
    private String descriptions;

    @Column(name = "prixenchere")
    private double prixEnchere;

    @Column(name = "idutilisateur")
    private int idUtilisateur;

    @Column(name = "idcommission")
    private int idCommission;

    @Column(name = "idcategorie")
    private int idCategorie;

    @Column(name = "dateenchere")
    private LocalDateTime dateEnchere;

    @Column(name = "duree")
    private Time duree;

    public boolean isTerminated(){

        LocalTime time1 = this.dateEnchere.toLocalTime();
        LocalTime time2 = LocalTime.of(duree.getHours(), duree.getMinutes(), duree.getSeconds());
        LocalTime totalTime = time1.plusHours(time2.getHour()).plusMinutes(time2.getMinute()).plusSeconds(time2.getSecond());
        LocalDateTime dateTime = LocalDateTime.of(this.dateEnchere.toLocalDate(), totalTime);
        Timestamp t = Timestamp.valueOf(dateTime);
        Timestamp t2 = Timestamp.valueOf(LocalDateTime.now());
        System.out.println("Eto "+dateTime+"    "+LocalDateTime.now());
        System.out.println("Eto "+t.getTime()+"    "+t2.getTime());
        if( t.getTime() <= t2.getTime()){
            return true;
        }
        return false;
    }
}
