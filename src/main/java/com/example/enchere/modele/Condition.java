package com.example.enchere.modele;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Condition {
    
    String nom;
    LocalDateTime dateMin;
    LocalDateTime dateMax;
    int idCategorie;
    double prixMin;
    double prixMax;
    int status;

    public String conditionRequete(){
        String condition = "";
        if( nom != null ){
            condition += " AND LOWER(nom) LIKE LOWER(CONCAT('%', '"+nom+"', '%')) ";
        }
        if( dateMin != null && dateMax == null){
            condition += " AND dateEnchere >='"+dateMin+"' ";
        }
        if( dateMin == null && dateMax != null){
            condition += " AND dateEnchere+duree <='"+dateMax+"' ";
        }
        if( dateMin != null && dateMax != null){
            condition += " AND ( dateEnchere >='"+dateMin+"' AND dateEnchere+duree <='"+dateMax+"' ) ";
        }
        if( idCategorie != 0 ){
            condition += " AND idCategorie = "+idCategorie+" ";
        }
        if( prixMin != 0.0 && prixMax == 0.0 ){
            condition += " AND prixEnchere >= "+prixMin+" ";
        }
        if( prixMin == 0.0 && prixMax != 0.0 ){
            condition += " AND prixEnchere <= "+prixMax+" ";
        }
        if( prixMin != 0.0 && prixMax != 0.0 ){
            condition += " AND ( prixEnchere >= "+prixMin+" AND prixEnchere <= "+prixMax+" ) ";
        }
        if( status != 0 ){
            Status s = new Status();
            condition += " AND statusEnchere = '"+s.valueOfStatus(status)+"' ";
        }
        System.out.println("Cdt "+condition);
        return condition;
    }
}
