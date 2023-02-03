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
import com.example.enchere.modele.ImageEnchere;
import com.example.enchere.repository.ImageEnchereRepository;
import com.example.enchere.retour.ErrorRetour;
import com.example.enchere.retour.SuccessRetour;

@RestController
@RequestMapping("imageenchere")
public class ImageEnchereController {
    @Autowired
    private ImageEnchereRepository imageEnchereRepository;

    @GetMapping("/listeImage")
    public @ResponseBody Map<String, Object> getAllEnchere(){
        try{
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("data", imageEnchereRepository.findAll());
            return data; 
        }
        catch(Exception e){
            throw new RessourceException(new ErrorRetour("Veuillez vérifier les informations", HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PostMapping("/insertImage")
    public @ResponseBody Map<String, Object> createEnchere(@RequestBody ImageEnchere image) throws Exception{
        try{
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("data", imageEnchereRepository.save(image));
            return data;
        }
        catch(Exception e){
            throw new RessourceException(new ErrorRetour("Veuillez vérifier les informations",HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PutMapping("modifier/{idImageEnchere}")
    public @ResponseBody Map<String, Object> updateEnchere(@PathVariable int idImageEnchere,@RequestBody ImageEnchere image) {
        ImageEnchere updateImage = imageEnchereRepository.findById(idImageEnchere).orElseThrow(() 
            -> new RessourceException(new ErrorRetour("idImageEnchere : "+idImageEnchere+" n'existe pas",HttpStatus.NO_CONTENT.value()))
        );
        updateImage.setNomImage(image.getNomImage());
        updateImage.setFormat(image.getFormat());
        updateImage.setIdEnchere(image.getIdEnchere());
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("data", imageEnchereRepository.save(updateImage));
        return data;
    }

    @DeleteMapping("delete/{idImageEnchere}")
    public @ResponseBody Map<String, Object> deleteEnchere(@PathVariable int idImageEnchere)throws Exception{
        ImageEnchere enchere = imageEnchereRepository.findById(idImageEnchere).orElseThrow(() 
            -> new RessourceException(new ErrorRetour("idImageEnchere : "+idImageEnchere+" n'existe pas",HttpStatus.NOT_FOUND.value()))
        );
        imageEnchereRepository.delete(enchere);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("data", new SuccessRetour(" l'idImageEnchere  "+idImageEnchere+" a été supprimé avec succès"));
        return data;
    }
}
