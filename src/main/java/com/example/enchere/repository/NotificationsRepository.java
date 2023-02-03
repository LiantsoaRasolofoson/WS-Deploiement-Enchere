package com.example.enchere.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.enchere.modele.Notifications;

public interface NotificationsRepository extends JpaRepository<Notifications, Integer> {
    
    @Query(value = "SELECT count(*) FROM Notifications WHERE idUtilisateur = ?1 AND etat = 0", nativeQuery = true)
    public int countNotifications(int idUtilisateur);

    @Query(value = "SELECT * FROM Notifications WHERE idUtilisateur = ?1 ", nativeQuery = true)
    List<Notifications> getAll(int idUtilisateur);
}
