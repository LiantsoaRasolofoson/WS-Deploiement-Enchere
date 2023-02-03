package com.example.enchere.retour;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorRetour {
    
    private String message;
    private int code;

}
