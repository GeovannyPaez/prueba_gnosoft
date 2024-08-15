package com.example.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Articulo;
import com.example.demo.services.ArticuloService;

@RestController
@RequestMapping("articulos")
public class ArticuloController {

    private final ArticuloService articuloService;

    public ArticuloController(ArticuloService articuloService) {
        this.articuloService = articuloService;
    }

    @GetMapping
    public Iterable<Articulo> getArticulos() {
        return this.articuloService.getAllArticulos();
    }

}
