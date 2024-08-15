package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// CREATE TABLE facturas (
//     id_factura SERIAL PRIMARY KEY,
//     numero_factura VARCHAR(20) UNIQUE NOT NULL,
//     id_cliente INTEGER NOT NULL,
//     fecha DATE NOT NULL,
//     subtotal DECIMAL(10, 2) NOT NULL CHECK (subtotal >= 0),
//     iva DECIMAL(10, 2) NOT NULL CHECK (iva >= 0),
//     total DECIMAL(10, 2) NOT NULL,
//     FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente)
// );
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Factura {

    private int id_factura;
    private String numero_factura;
    private int id_cliente;
    private String fecha;
    private double subtotal;
    private double iva;
    private double total;

}
