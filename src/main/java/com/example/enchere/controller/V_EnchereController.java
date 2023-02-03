package com.example.enchere.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.enchere.exeption.RessourceException;
import com.example.enchere.modele.V_Enchere;
import com.example.enchere.repository.V_EnchereRepository;
import com.example.enchere.retour.ErrorRetour;

@RestController
@RequestMapping("/v_enchere")
public class V_EnchereController {

    @Autowired
    private V_EnchereRepository enchereRepository;

    @GetMapping("/listeEncheres")
    public @ResponseBody Map<String, Object> getAllEnchere() {
        try {
            Map<String, Object> data = new HashMap<String, Object>();
            List <V_Enchere> listes = enchereRepository.findAll();
            for(V_Enchere e : listes){
                e.setImages(e.allImages());
                e.setOneImage(e.oneImages());
            }
            data.put("data",listes);
            return data;
        } catch (Exception e) {
            throw new RessourceException(
                    new ErrorRetour("Veuillez vérifier les informations", HttpStatus.BAD_REQUEST.value()));
        }
    }

    @GetMapping("/listeEncheresEnCours")
    public @ResponseBody Map<String, Object> getAllEnchereEnCours() {
        try {
            Map<String, Object> data = new HashMap<String, Object>();
            List <V_Enchere> listes = enchereRepository.getAllEnCours();
            for(V_Enchere e : listes){
                e.setImages(e.allImages());
                e.setOneImage(e.oneImages());
            }
            data.put("data",listes);
            return data;
        } catch (Exception e) {
            throw new RessourceException(
                    new ErrorRetour("Veuillez vérifier les informations", HttpStatus.BAD_REQUEST.value()));
        }
    }

    @GetMapping("/listeEncheresTermine")
    public @ResponseBody Map<String, Object> getAllTermine() {
        try {
            Map<String, Object> data = new HashMap<String, Object>();
            List <V_Enchere> listes = enchereRepository.getAllTermine();
            for(V_Enchere e : listes){
                e.setImages(e.allImages());
                e.setOneImage(e.oneImages());
            }
            data.put("data",listes);
            return data;
        } catch (Exception e) {
            throw new RessourceException(
                    new ErrorRetour("Veuillez vérifier les informations", HttpStatus.BAD_REQUEST.value()));
        }
    }

    @GetMapping("/listeEncheresNonDemarrer")
    public @ResponseBody Map<String, Object> getAllNonDemarrer() {
        try {
            Map<String, Object> data = new HashMap<String, Object>();
            List <V_Enchere> listes = enchereRepository.getAllNonDemarrer();
            for(V_Enchere e : listes){
                e.setImages(e.allImages());
                e.setOneImage(e.oneImages());
            }
            data.put("data",listes);
            return data;
        } catch (Exception e) {
            throw new RessourceException(
                    new ErrorRetour("Veuillez vérifier les informations", HttpStatus.BAD_REQUEST.value()));
        }
    }
}
