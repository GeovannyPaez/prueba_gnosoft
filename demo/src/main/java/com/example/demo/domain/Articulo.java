package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// CREATE TABLE articulos (
//     id_articulo SERIAL PRIMARY KEY,
//     nombre VARCHAR(100) NOT NULL,
//     precio DECIMAL(10, 2) NOT NULL
// );

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Articulo {

    private int id_articulo;
    private String nombre;
    private double precio;
}
