package com.example.enchere.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.enchere.modele.Enchere;
import com.example.enchere.modele.EnchereVendu;
import com.example.enchere.modele.Notifications;
import com.example.enchere.modele.Offre;
import com.example.enchere.modele.Utilisateur;
import com.example.enchere.repository.EnchereRepository;
import com.example.enchere.repository.EnchereVenduRepository;
import com.example.enchere.repository.NotificationsRepository;
import com.example.enchere.repository.OffreRepository;

@Component
public class ServiceEnchere implements CommandLineRunner{

    @Autowired
    private NotificationService serviceNotification;

    private int idUtilisateur;

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    @Override
    public void run(String... args)throws Exception{
        while(true){
            serviceNotification.finEnchere();
            Thread.sleep(5000);
        }
    }
}
