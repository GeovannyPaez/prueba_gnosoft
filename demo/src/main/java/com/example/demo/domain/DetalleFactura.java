package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// CREATE TABLE detalles_factura (
//     id_detalle SERIAL PRIMARY KEY,
//     id_factura INTEGER NOT NULL,
//     id_articulo INTEGER NOT NULL,
//     cantidad INTEGER NOT NULL CHECK (cantidad > 0),
//     precio_unitario DECIMAL(10, 2) NOT NULL,
//     valor DECIMAL(10, 2) GENERATED ALWAYS AS (cantidad * precio_unitario) STORED,
//     FOREIGN KEY (id_factura) REFERENCES facturas(id_factura) ON DELETE CASCADE,
//     FOREIGN KEY (id_articulo) REFERENCES articulos(id_articulo)
// );

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class DetalleFactura {

    private int id_detalle;
    private int id_factura;
    private int id_articulo;
    private int cantidad;
    private double precio_unitario;
    private double valor;

}
