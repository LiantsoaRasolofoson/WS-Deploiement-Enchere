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
@Table(name = "notifications")
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idnotifications")
    private int idNotifications;

    @Column(name = "idutilisateur")
    private int idUtilisateur;

    @Column(name = "notifications")
    private String notifications;

    @Column(name = "etat")
    private int etat;
    
}
