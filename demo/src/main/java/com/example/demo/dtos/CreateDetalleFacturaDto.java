package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateDetalleFacturaDto {

    private int id_articulo;
    private int cantidad;
    private double precio_unitario;

    public double getTotal() {
        return this.cantidad * this.precio_unitario;
    }
}
