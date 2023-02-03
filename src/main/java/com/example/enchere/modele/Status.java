package com.example.enchere.modele;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Status {
    
    int idStatus;
    String valeur;


    public String valueOfStatus(int id){
        String value = "";
        if( id == 1 ){
            value = "Non demarrer";
        }
        if( id == 2 ){
            value = "En Cours";
        }
        if( id == 3 ){
            value = "Termine";
        }
        return value;
    }
}
