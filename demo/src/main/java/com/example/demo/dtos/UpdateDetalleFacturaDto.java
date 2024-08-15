package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// {
//     "id_detalle": 8,
//     "id_factura": 5,
//     "id_articulo": 2,
//     "cantidad": 20,
//     "precio_unitario": 20.5,
//     "valor": 410.0
// }
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateDetalleFacturaDto {
    private int id_detalle;
    private int id_factura;
    private int id_articulo;
    private int cantidad;
    private double precio_unitario;

}
