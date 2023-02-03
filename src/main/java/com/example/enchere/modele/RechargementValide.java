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
@Table(name = "rechargementvalide")

public class RechargementValide {
    
    @Id
    @Column(name = "idrechargement")
    private int idRechargement;

    @Column(name = "datevalidation")
    private Date dateValidation;

   

}
