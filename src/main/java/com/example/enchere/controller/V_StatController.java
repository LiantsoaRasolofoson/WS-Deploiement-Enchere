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
import com.example.enchere.modele.V_Stat;
import com.example.enchere.repository.V_StatRepository;
import com.example.enchere.retour.ErrorRetour;

@RestController
@RequestMapping("/statistique")
public class V_StatController {
    @Autowired
    V_StatRepository v_StatRepository;

    @GetMapping
    public @ResponseBody Map<String, Object> getStat() {
        try {

            Map<String, Object> data = new HashMap<String, Object>();
            List<V_Stat> stat = v_StatRepository.findAll();
            if (stat.size() > 0) {
                data.put("data", stat);
            } else {
                data.put("message", "Aucune enchère vendue");
            }

            return data;
        } catch (Exception e) {
            throw new RessourceException(
                    new ErrorRetour("Veuillez vérifier les informations", HttpStatus.BAD_REQUEST.value()));
        }
    }
}
