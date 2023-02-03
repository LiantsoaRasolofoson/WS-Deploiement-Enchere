package com.example.enchere.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.enchere.exeption.RessourceException;
import com.example.enchere.modele.Compte;
import com.example.enchere.modele.HistoriqueEnchere;
import com.example.enchere.modele.Offre;
import com.example.enchere.modele.Token;
import com.example.enchere.modele.Utilisateur;
import com.example.enchere.modele.V_Enchere;
import com.example.enchere.repository.CompteRepository;
import com.example.enchere.repository.EnchereRepository;
import com.example.enchere.repository.HistoriqueEnchereRepository;
import com.example.enchere.repository.OffreRepository;
import com.example.enchere.repository.TokenRepository;
import com.example.enchere.repository.UtilisateurRepository;
import com.example.enchere.repository.V_EnchereRepository;
import com.example.enchere.retour.ErrorRetour;
import com.example.enchere.retour.SuccessRetour;

@RestController
@RequestMapping("/offres")
public class OffreController {

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private OffreRepository offreRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private V_EnchereRepository v_enchereRepository;

    @Autowired
    private HistoriqueEnchereRepository historiqueEnchereRepository;

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

    public HistoriqueEnchere getHistoriqueEnchere(Offre offre, Utilisateur utilisateur) {
        HistoriqueEnchere he = new HistoriqueEnchere();
        he.setIdEnchere(offre.getIdEnchere());
        he.setIdOffre(offre.getIdOffre());
        he.setPrixOffre(offre.getPrixOffre());
        he.setDateOffre(offre.getDateOffre());
        he.setUtilisateur(utilisateur);
        return he;
    }

    public void saveOffreUser(Offre max, Offre offre) throws Exception {
        V_Enchere enchere = v_enchereRepository.getEnchere(offre.getIdEnchere());
        offre.checkUser(enchere);
        if (max != null) {
            offre.checkMontant(max);
        } else {
            offre.checkMontant(enchere);
        }
        Utilisateur utilisateur = utilisateurRepository.findById(offre.getIdUtilisateur())
                .orElseThrow(() -> new RessourceException(
                        new ErrorRetour("Le idUtilisateur : " + offre.getIdUtilisateur() + " n'existe pas",
                                HttpStatus.NO_CONTENT.value())));
        Compte userCompte = compteRepository.getCompte(offre.getIdUtilisateur());
        userCompte.checkSolde(offre.getPrixOffre());
        offre.setDateOffre(LocalDateTime.now());
        compteRepository.save(userCompte);
        offreRepository.save(offre);
        HistoriqueEnchere he = this.getHistoriqueEnchere(offre, utilisateur);
        historiqueEnchereRepository.save(he);
    }

    public void updateLastOffre(Offre max) throws Exception {
        Compte last = compteRepository.getCompte(max.getIdUtilisateur());
        last.setSolde(last.getSolde() + max.getPrixOffre());
        compteRepository.save(last);
    }

    @PostMapping("/rencherir/{tokenValues}")
    public @ResponseBody Map<String, Object> rencherir(@RequestBody Offre offre, @PathVariable String tokenValues)
            throws Exception {
        try {
            isTokenExipered(tokenValues);
            Token t = tokenRepository.getToken(tokenValues);
            offre.setIdUtilisateur(t.getIdUtilisateur());
            V_Enchere e = v_enchereRepository.getEnchere(offre.getIdEnchere());
            e.isEncherable();
            Offre max = offreRepository.getOffreMax(offre.getIdEnchere());
            offre.setDateOffre(LocalDateTime.now());
            saveOffreUser(max, offre);
            if (max != null) {
                updateLastOffre(max);
            }
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("data", historiqueEnchereRepository.getHistoriques(e.getIdEnchere()));
            return data;
        } catch (Exception e) {
            throw e;
        }
    }

    // @GetMapping("/listeOffre")
    // public @ResponseBody Map<String, Object> getAllOffre(){
    // try{
    // Map<String, Object> data = new HashMap<String, Object>();
    // data.put("data", offreRepository.findAll());
    // return data;
    // }
    // catch(Exception e){
    // throw new RessourceException(new ErrorRetour("Veuillez vérifier les
    // informations", HttpStatus.BAD_REQUEST.value()));
    // }
    // }

    // @PostMapping("/insertionOffre")
    // public @ResponseBody Map<String, Object> createOffre(@RequestBody Offre
    // offre) throws Exception{
    // try{
    // Map<String, Object> data = new HashMap<String, Object>();
    // data.put("data", offreRepository.save(offre));
    // return data;
    // }
    // catch(Exception e){
    // throw new RessourceException(new ErrorRetour("Veuillez vérifier les
    // informations",HttpStatus.BAD_REQUEST.value()));
    // }
    // }

    // @PutMapping("modifier/{idOffre}")
    // public @ResponseBody Map<String, Object> updateOffre(@PathVariable int
    // idOffre,@RequestBody Offre offre) {
    // Offre updateOffre = offreRepository.findById(idOffre).orElseThrow(()
    // -> new RessourceException(new ErrorRetour("idOffre : "+idOffre+" n'existe
    // pas",HttpStatus.NO_CONTENT.value()))
    // );
    // updateOffre.setIdEnchere(offre.getIdEnchere());
    // updateOffre.setIdUtilisateur(offre.getIdUtilisateur());
    // updateOffre.setPrixOffre(offre.getPrixOffre());
    // updateOffre.setDateOffre(offre.getDateOffre());
    // Map<String, Object> data = new HashMap<String, Object>();
    // data.put("data", offreRepository.save(updateOffre));
    // return data;
    // }

    // @DeleteMapping("delete/{idOffre}")
    // public @ResponseBody Map<String, Object> deleteOffre(@PathVariable int
    // idOffre)throws Exception{
    // Offre offre = offreRepository.findById(idOffre).orElseThrow(()
    // -> new RessourceException(new ErrorRetour("idOffre : "+idOffre+" n'existe
    // pas",HttpStatus.NOT_FOUND.value()))
    // );
    // offreRepository.delete(offre);
    // Map<String, Object> data = new HashMap<String, Object>();
    // data.put("data", new SuccessRetour(" l'idOffre "+idOffre+" a été supprimé
    // avec succès"));
    // return data;
    // }
}
