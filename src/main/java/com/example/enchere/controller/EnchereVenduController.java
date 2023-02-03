package com.example.enchere.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.enchere.exeption.RessourceException;
import com.example.enchere.modele.EnchereVendu;
import com.example.enchere.repository.EnchereVenduRepository;
import com.example.enchere.retour.ErrorRetour;
import com.example.enchere.retour.SuccessRetour;

@RestController
@RequestMapping("/encherevendu")
public class EnchereVenduController {
    @Autowired
    private EnchereVenduRepository enchereVenduRepository;

    @GetMapping("/liste")
    public @ResponseBody Map<String, Object> getAllEnchereVendu(){
        try{
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("data", enchereVenduRepository.findAll());
            return data; 
        }
        catch(Exception e){
            throw new RessourceException(new ErrorRetour("Veuillez vérifier les informations", HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PostMapping("/insertion")
    public @ResponseBody Map<String, Object> createEnchere(@RequestBody EnchereVendu enchere) throws Exception{
        try{
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("data", enchereVenduRepository.save(enchere));
            return data;
        }
        catch(Exception e){
            throw new RessourceException(new ErrorRetour("Veuillez vérifier les informations",HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PutMapping("modifier/{idEnchere}")
    public @ResponseBody Map<String, Object> updateEnchere(@PathVariable int idEnchere,@RequestBody EnchereVendu enchere) {
        EnchereVendu updateEnchere = enchereVenduRepository.findById(idEnchere).orElseThrow(() 
            -> new RessourceException(new ErrorRetour("idEnchere : "+idEnchere+" n'existe pas",HttpStatus.NO_CONTENT.value()))
        );
        updateEnchere.setIdEnchere(enchere.getIdEnchere());
        updateEnchere.setIdOffre(enchere.getIdOffre());
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("data", enchereVenduRepository.save(updateEnchere));
        return data;
    }

    @DeleteMapping("delete/{idEnchere}")
    public @ResponseBody Map<String, Object> deleteEnchere(@PathVariable int idEnchere)throws Exception{
        EnchereVendu enchere = enchereVenduRepository.findById(idEnchere).orElseThrow(() 
            -> new RessourceException(new ErrorRetour("idEnchere : "+idEnchere+" n'existe pas",HttpStatus.NOT_FOUND.value()))
        );
        enchereVenduRepository.delete(enchere);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("data", new SuccessRetour(" l'idEnchere  "+idEnchere+" a été supprimé avec succès"));
        return data;
    }
}
