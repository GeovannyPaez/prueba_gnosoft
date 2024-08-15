package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateFacturaDto {

    private String numero_factura;
    private int id_cliente; // Usamos ID del cliente en lugar del nombre para evitar posibles
                            // inconsistencias y para facilitar la búsqueda
    private String fecha;
    private List<CreateDetalleFacturaDto> detalles;

}
