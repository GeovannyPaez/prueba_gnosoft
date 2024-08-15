package com.example.demo.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateFacturaDto {

    private String numero_factura;
    private int id_cliente; // Usamos ID del cliente en lugar del nombre para evitar posibles
                            // inconsistencias y para facilitar la búsqueda
    private String fecha;
    private List<UpdateDetalleFacturaDto> detalles;

}
