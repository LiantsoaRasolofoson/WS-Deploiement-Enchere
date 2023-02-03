package com.example.enchere.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.enchere.exeption.RessourceException;
import com.example.enchere.modele.Compte;
import com.example.enchere.modele.Rechargement;
import com.example.enchere.modele.RechargementValide;
import com.example.enchere.modele.Token;
import com.example.enchere.modele.V_Rechargement;
import com.example.enchere.repository.CompteRepository;
import com.example.enchere.repository.RechargementRepository;
import com.example.enchere.repository.RechargementValideRepository;
import com.example.enchere.repository.TokenRepository;
import com.example.enchere.repository.V_RechargementRepository;
import com.example.enchere.retour.ErrorRetour;

@RestController
@RequestMapping("/rechargement")
public class V_RechargementController {

    @Autowired
    private V_RechargementRepository v_rechargementRepository;

    @Autowired
    private RechargementRepository rechargementRepository;

    @Autowired
    private RechargementValideRepository rechargementValideRepository;

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private TokenRepository tokenRepository;

    public void isTokenExipered(String tokenValues) throws Exception {
        try{
            if( tokenValues.equals("null")){
                throw new RessourceException(new ErrorRetour("Vous devez vous connecter",HttpStatus.BAD_REQUEST.value()));
            }
            Token token = tokenRepository.getToken(tokenValues);
            token.bearerToken(token);
        }
        catch(Exception e){
            throw e;
        }
    }

    @GetMapping("/listeNonValide")
    public @ResponseBody Map<String, Object> nonValides() {
        try {
            Map<String, Object> data = new HashMap<String, Object>();
            List<V_Rechargement> nonValide = v_rechargementRepository.nonValides();
            if (nonValide.size() > 0) {
                data.put("data", nonValide);
            } else {
                data.put("message", "Aucun rechargement non validé");
            }
            return data;
        } catch (Exception e) {
            throw new RessourceException(
                    new ErrorRetour("Veuillez vérifier les informations", HttpStatus.BAD_REQUEST.value()));
        }
    }

    @GetMapping("/listeValide")
    public @ResponseBody Map<String, Object> valides() {
        try {
            Map<String, Object> data = new HashMap<String, Object>();
            List<V_Rechargement> valide = v_rechargementRepository.nonValides();
            if (valide.size() > 0) {
                data.put("data", valide);
            } else {
                data.put("message", "Aucun rechargement validé");
            }
            return data;
        } catch (Exception e) {
            throw new RessourceException(
                    new ErrorRetour("Veuillez vérifier les informations", HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PutMapping("validationRechargement")
    public @ResponseBody Map<String, Object> confirmeRechargement(@RequestParam("idRechargement") int idRechargement) {
        try {
            Rechargement rg = rechargementRepository.findById(idRechargement).get();
            Compte compte = compteRepository.findById(rg.getIdCompte()).get();
            double nouveauSolde = compte.getSolde() + rg.getMontant();
            compte.setSolde(nouveauSolde);
            compteRepository.save(compte);
            Date dateValidation = Date.valueOf(LocalDate.now());
            RechargementValide rv = new RechargementValide(idRechargement, dateValidation);
            rechargementValideRepository.save(rv);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("data", "Confirmation réussie, rechargement validé");
            return data;
        } catch (Exception e) {
            throw new RessourceException(new ErrorRetour("Erreur de la confirmation", HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PostMapping("/rechargement/{tokenValues}")
    public @ResponseBody Map<String, Object> rechargement(@RequestBody Rechargement rechargement,@PathVariable String tokenValues) throws Exception {
        try {
            isTokenExipered(tokenValues);
            Token t = tokenRepository.getToken(tokenValues);
            Compte compte = compteRepository.getCompte(t.getIdUtilisateur());
            System.out.println("Compte "+compte.getSolde());
            rechargement.setIdCompte(compte.getIdCompte());
            rechargement.setDateRechargement(Date.valueOf(LocalDate.now()));
            Map<String, Object> data = new HashMap<String, Object>();
            if (rechargement.getMontant() > 0) {
                data.put("data", rechargementRepository.save(rechargement));
            } else {
                throw new RessourceException(new ErrorRetour("Montant Invalide", HttpStatus.BAD_REQUEST.value()));
            }
            return data;
        } catch (Exception e) {
            throw e;
        }
    }
}
