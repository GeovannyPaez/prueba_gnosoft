package com.example.demo.services;

import org.springframework.stereotype.Service;

import com.example.demo.dao.ArticuloDao;
import com.example.demo.domain.Articulo;

@Service
public class ArticuloService {

    private final ArticuloDao articuloDao;

    public ArticuloService(ArticuloDao articuloDao) {
        this.articuloDao = articuloDao;
    }

    public Iterable<Articulo> getAllArticulos() {
        return this.articuloDao.getAll();
    }
}
