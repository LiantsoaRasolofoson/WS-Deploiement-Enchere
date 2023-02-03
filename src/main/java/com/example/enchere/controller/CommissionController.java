package com.example.enchere.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.enchere.exeption.RessourceException;
import com.example.enchere.modele.Commission;
import com.example.enchere.repository.CommissionRepository;
import com.example.enchere.retour.ErrorRetour;

@RestController
@RequestMapping("/commission")
public class CommissionController {

    @Autowired
    private CommissionRepository commissionRepository;

    @PostMapping("/insertion")
    public @ResponseBody Map<String, Object> login(@RequestBody Commission commission) throws Exception {
        try {
            Map<String, Object> data = new HashMap<String, Object>();
            commission.setDateCommission(Date.valueOf(LocalDate.now()));
            data.put("data", commissionRepository.save(commission));
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
            data.put("data", commissionRepository.findAll());
            return data;
        } catch (Exception e) {
            throw new RessourceException(
                    new ErrorRetour("Veuillez vérifier les informations", HttpStatus.NOT_FOUND.value()));
        }
    }
}
