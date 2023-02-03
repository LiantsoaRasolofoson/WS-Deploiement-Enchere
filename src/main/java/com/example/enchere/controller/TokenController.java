package com.example.enchere.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.enchere.repository.*;
import com.example.enchere.retour.ErrorRetour;
import com.example.enchere.retour.SuccessRetour;
import com.example.enchere.modele.*;
import com.example.enchere.exeption.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/tokens")
public class TokenController {

    @Autowired
    private TokenRepository tokenRepository;

    @GetMapping("{idUtilisateur}")
    public @ResponseBody Map<String, Object> bearerToken(@PathVariable int idUtilisateur) {
        Token token = tokenRepository.getTokenByUserNotExpired(idUtilisateur);
        if (token != null) {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("data", new SuccessRetour("Bearer Token Ok"));
            return data;
        } else {
            throw new RessourceException(new ErrorRetour("Bearer Token Non Valide", HttpStatus.NOT_FOUND.value()));
        }
    }

}