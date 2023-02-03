package com.example.enchere.modele;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnchereInsertion {
    
    public Enchere enchere;
    public ImageEnchere [] images;
}
