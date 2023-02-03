package com.example.enchere.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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
import com.example.enchere.modele.Commission;
import com.example.enchere.modele.Condition;
import com.example.enchere.modele.Enchere;
import com.example.enchere.modele.EnchereInsertion;
import com.example.enchere.modele.ImageEnchere;
import com.example.enchere.modele.Status;
import com.example.enchere.modele.Token;
import com.example.enchere.modele.V_Enchere;
import com.example.enchere.repository.EnchereRepository;
import com.example.enchere.repository.GagnantRepository;
import com.example.enchere.repository.HistoriqueEnchereRepository;
import com.example.enchere.repository.ImageEnchereRepository;
import com.example.enchere.repository.TokenRepository;
import com.example.enchere.repository.V_EnchereRepository;
import com.example.enchere.repository.CommissionRepository;
import com.example.enchere.retour.ErrorRetour;
import com.example.enchere.retour.SuccessRetour;

@RestController
@RequestMapping("/encheres")
public class EnchereController {

    @Autowired
    private HistoriqueEnchereRepository historiqueEnchereRepository;

    @Autowired
    private V_EnchereRepository v_enchereRepository;

    @Autowired
    private ImageEnchereRepository imageEnchereRepository;

    @Autowired
    private EnchereRepository enchereRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private GagnantRepository gagnantRepository;

    @Autowired
    private CommissionRepository commissionRepository;

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

    V_Enchere v = new V_Enchere();

    Status[] status = {
            new Status(1, "Non demarrer"),
            new Status(2, "En Cours"),
            new Status(3, "Termine")
    };

    @PostMapping("/insertionEnchere/{tokenValues}")
    public @ResponseBody Map<String, Object> createEnchere(@RequestBody EnchereInsertion enchereInsertion,
            @PathVariable String tokenValues) throws Exception {
        try {
            isTokenExipered(tokenValues);
            Token t = tokenRepository.getToken(tokenValues);
            Commission c = commissionRepository.getCommission();
            Map<String, Object> data = new HashMap<String, Object>();
            Enchere enchere = enchereInsertion.getEnchere();
            enchere.setDateEnchere(LocalDateTime.now());
            enchere.setIdCommission(c.getIdCommission());
            enchere.setIdUtilisateur(t.getIdUtilisateur());
            Enchere e = insertionImage(enchere, enchereInsertion.getImages());
            data.put("data", e);
            return data;
        } catch (Exception e) {
            throw e;
        }
    }

    public Enchere insertionImage(Enchere enchere, ImageEnchere[] images) {
        try {
            if( images.length == 0 ){
                throw new RessourceException(new ErrorRetour("Veuillez vérifier les informations", HttpStatus.BAD_REQUEST.value()));
            }
            else{
                Enchere e = enchereRepository.save(enchere);
                for (int i = 0; i < images.length; i++) {
                    images[i].setIdEnchere(enchere.getIdEnchere());
                    imageEnchereRepository.save(images[i]);
                }
                return e;
            }
        } catch (Exception ex) {
            throw new RessourceException(new ErrorRetour("Insertion Image : " + ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PutMapping("modifier/{idEnchere}")
    public @ResponseBody Map<String, Object> updateEnchere(@PathVariable int idEnchere, @RequestBody Enchere enchere) {
        Enchere updateEnchere = enchereRepository.findById(idEnchere).orElseThrow(() -> new RessourceException(
                new ErrorRetour("idEnchere : " + idEnchere + " n'existe pas", HttpStatus.NO_CONTENT.value())));
        updateEnchere.setNom(enchere.getNom());
        updateEnchere.setDescriptions(enchere.getDescriptions());
        updateEnchere.setPrixEnchere(enchere.getPrixEnchere());
        updateEnchere.setIdUtilisateur(enchere.getIdUtilisateur());
        updateEnchere.setIdCommission(enchere.getIdCommission());
        updateEnchere.setIdCategorie(enchere.getIdCategorie());
        updateEnchere.setDateEnchere(enchere.getDateEnchere());
        updateEnchere.setDuree(enchere.getDuree());
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("data", enchereRepository.save(updateEnchere));
        return data;
    }

    @DeleteMapping("delete/{idEnchere}")
    public @ResponseBody Map<String, Object> deleteEnchere(@PathVariable int idEnchere) throws Exception {
        Enchere enchere = enchereRepository.findById(idEnchere).orElseThrow(() -> new RessourceException(
                new ErrorRetour("idEnchere : " + idEnchere + " n'existe pas", HttpStatus.NOT_FOUND.value())));
        enchereRepository.delete(enchere);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("data", new SuccessRetour(" l'idEnchere  " + idEnchere + " a été supprimé avec succès"));
        return data;
    }

    @PostMapping("/recherches")
    public @ResponseBody Map<String, Object> recherches(@RequestBody Condition condition) {
        try {
            String c = condition.conditionRequete();
            Map<String, Object> data = new HashMap<String, Object>();
            List<V_Enchere> liste = v.getAll(c);
            for( V_Enchere e : liste ){
                System.out.println(e.getPrixEnchere());
                e.setImages(e.allImages());
                e.setOneImage(e.oneImages());
            }
            System.out.println("Eto t "+liste.size());
            data.put("data", liste);
            return data;
        } catch (Exception e) {
            throw new RessourceException(new ErrorRetour(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @GetMapping("/historiques/{idEnchere}/{tokenValues}")
    public @ResponseBody Map<String, Object> historiques(@PathVariable int idEnchere, @PathVariable String tokenValues)
            throws Exception {
        try {
            System.out.println("Eto "+idEnchere);
            isTokenExipered(tokenValues);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("data", historiqueEnchereRepository.getHistoriques(idEnchere));
            return data;
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/listeEnchere/{tokenValues}")
    public @ResponseBody Map<String, Object> listeEnchere(@PathVariable String tokenValues) {
        try {
            isTokenExipered(tokenValues);
            Token t = tokenRepository.getToken(tokenValues);
            Map<String, Object> data = new HashMap<String, Object>();
            List <V_Enchere> listes =  v_enchereRepository.getAll(t.getIdUtilisateur());
            data.put("data", listes);
            for(V_Enchere e: listes){
                e.setImages(e.allImages());
                e.setOneImage(e.oneImages());
            }
            return data;
        } catch (Exception e) {
            throw new RessourceException(new ErrorRetour(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @GetMapping("{idEnchere}")
    public @ResponseBody Map<String, Object> detail(@PathVariable int idEnchere) {
        System.out.println("Eto "+idEnchere);
        try {
            Map<String, Object> data = new HashMap<String, Object>();
            V_Enchere enchere = v_enchereRepository.getEnchere(idEnchere);
            enchere.setOneImage(enchere.oneImages());
            enchere.setImages(enchere.allImages());
            data.put("enchere", enchere);
            data.put("historiques", historiqueEnchereRepository.getHistoriques(idEnchere));
            data.put("gagnant", gagnantRepository.getGagnant(idEnchere));
            return data;
        } catch (Exception e) {
            throw new RessourceException(
                new ErrorRetour("Veuillez vérifier les informations", HttpStatus.BAD_REQUEST.value()));
        }
    }

}
