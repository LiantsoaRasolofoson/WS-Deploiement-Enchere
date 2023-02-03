package com.example.enchere.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.enchere.exeption.RessourceException;
import com.example.enchere.modele.Token;
import com.example.enchere.modele.Utilisateur;
import com.example.enchere.repository.TokenRepository;
import com.example.enchere.repository.UtilisateurRepository;
import com.example.enchere.retour.ErrorRetour;
import com.example.enchere.retour.SuccessRetour;

@RestController
@RequestMapping("/utilisateur")
public class UtilisateurController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @PostMapping("/loginUser")
    public @ResponseBody Map<String, Object> login(@RequestBody Utilisateur utilisateur) throws Exception {
        Utilisateur user = utilisateurRepository.login(utilisateur.getEmail(), utilisateur.getMotDePasse());
        if( user == null ){
            throw new RessourceException(new ErrorRetour("Compte innexistant",HttpStatus.NOT_FOUND.value()));
        }
        else{
            Token t = new Token().generateToken(user,1);
            tokenRepository.save(t);
            Map<String, Object> data = new HashMap<String, Object>();
            Token token = tokenRepository.getTokenByUserNotExpired(user.getIdUtilisateur());
            data.put("token", token);
            return data;
        }
    }

    @PostMapping("/inscription")
    public @ResponseBody Map<String, Object> inscription(@RequestBody Utilisateur utilisateur) throws Exception {
        try{
            Map<String, Object> data = new HashMap<String, Object>();
            utilisateur = utilisateurRepository.save(utilisateur);
            Token t = new Token().generateToken(utilisateur,1);
            tokenRepository.save(t);
            Token token = tokenRepository.getTokenByUserNotExpired(utilisateur.getIdUtilisateur());
            data.put("token", token);
            return data;
        }
        catch(Exception e){
            throw new RessourceException(new ErrorRetour("Inscription : "+e.getMessage(),HttpStatus.NOT_FOUND.value()));
        }
    }

    @GetMapping("{tokenValues}")
    public @ResponseBody Map<String, Object> deconnexion(@PathVariable String tokenValues){
        System.out.println("Here "+tokenValues);
        Token token = tokenRepository.getToken(tokenValues);
        if( token == null ){
            throw new RessourceException(new ErrorRetour("Token déja expiré",HttpStatus.NOT_FOUND.value()));
        }
        else{
            token.setDateExpiration(LocalDateTime.now());
            tokenRepository.save(token);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("data", new SuccessRetour("Déconnexion succès"));
            return data;
        }
    }

    
    
}
