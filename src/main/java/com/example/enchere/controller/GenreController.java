package com.example.enchere.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.enchere.repository.GenreRepository;

@RestController
@RequestMapping("/genres")
public class GenreController {
    

    @Autowired
    private GenreRepository genreRepository;

    @GetMapping
    public @ResponseBody Map<String, Object> genres() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("data", genreRepository.findAll());
        return data;
    }
}
