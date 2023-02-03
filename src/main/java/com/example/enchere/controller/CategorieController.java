package com.example.enchere.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.example.enchere.modele.Categorie;
import com.example.enchere.modele.Status;
import com.example.enchere.repository.CategorieRepository;
import com.example.enchere.repository.CommissionRepository;
import com.example.enchere.retour.ErrorRetour;
import com.example.enchere.retour.SuccessRetour;

@RestController
@RequestMapping("/categorie")
public class CategorieController {

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private CommissionRepository commissionRepository;

    Status [] status = {
        new Status(1, "Non demarrer"),
        new Status(2, "En Cours"),
        new Status(3, "Termine")
    };

    @PostMapping("/insertion")
    public @ResponseBody Map<String, Object> insertion(@RequestBody Categorie categorie) throws Exception {
        try {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("data", categorieRepository.save(categorie));
            return data;
        } catch (Exception e) {
            throw new RessourceException(
                    new ErrorRetour("Veuillez vérifier les informations", HttpStatus.NOT_FOUND.value()));
        }
    }

    @GetMapping("/liste")
    public @ResponseBody Map<String, Object> liste() throws Exception {
        try {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("data", categorieRepository.findAll());
            data.put("status", status);
            data.put("commission", commissionRepository.getCommission());
            return data;
        } 
        catch (Exception e) {
            throw new RessourceException(
                new ErrorRetour("Veuillez vérifier les informations", HttpStatus.NOT_FOUND.value())
            );
        }
    }

    @PutMapping("/updateCategorie")
    public @ResponseBody Map<String, Object> updateOffre(@RequestBody Categorie c) {
        Categorie updateCategorie = categorieRepository.findById(c.getIdCategorie()).orElseThrow(
                () -> new RessourceException(new ErrorRetour("idCategorie : " + c.getIdCategorie() + " n'existe pas",
                        HttpStatus.NO_CONTENT.value())));
        updateCategorie.setValeur(c.getValeur());
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("data", categorieRepository.save(updateCategorie));
        return data;
    }

    @DeleteMapping("{idCategorie}")
    public @ResponseBody Map<String, Object> deleteOffre(@PathVariable int idCategorie) throws Exception {

        try {
            Categorie categorie = categorieRepository.findById(idCategorie).orElseThrow(() -> new RessourceException(
                    new ErrorRetour("idCategorie : " + idCategorie + " n'existe pas", HttpStatus.NOT_FOUND.value())));
            categorieRepository.delete(categorie);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("data", new SuccessRetour(" l'idCategorie  " + idCategorie + " a été supprimé avec succès"));
            return data;
        } catch (Exception e) {
            throw new RessourceException(new ErrorRetour("Error " + e.getMessage(), HttpStatus.NOT_FOUND.value()));
        }
    }
}
