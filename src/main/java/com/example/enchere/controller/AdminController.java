package com.example.enchere.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.enchere.exeption.RessourceException;
import com.example.enchere.modele.Admins;
import com.example.enchere.repository.AdminsRepository;
import com.example.enchere.retour.ErrorRetour;
import com.example.enchere.retour.SuccessRetour;

@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private AdminsRepository adminsRepository;

    @PostMapping("/authentification")
    public @ResponseBody Map<String, Object> login(@RequestBody Admins admins) throws Exception {
        Admins a = adminsRepository.login(admins.getEmail(), admins.getMotDePasse());
        if (a == null) {
            throw new RessourceException(new ErrorRetour("Compte innexistant", HttpStatus.NOT_FOUND.value()));
        } else {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("data", new SuccessRetour("Vous êtes bien connectée " + a.getNom()));
            return data;
        }
    }

}
