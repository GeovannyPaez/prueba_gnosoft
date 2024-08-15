package com.example.demo.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.DetalleFactura;
import com.example.demo.domain.Factura;
import com.example.demo.dtos.CreateFacturaDto;
import com.example.demo.dtos.UpdateFacturaDto;
import com.example.demo.services.FacturaService;

@RestController
@RequestMapping("facturas")
public class FacturaContoller {

    private final FacturaService facturaService;

    public FacturaContoller(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @PostMapping
    public void createFactura(@RequestBody CreateFacturaDto factura) throws Exception {
        this.facturaService.createFactura(factura);
    }

    @GetMapping("/{id}")
    public Factura getFacturaById(@PathVariable String id) throws Exception {
        return this.facturaService.getFactura(Integer.parseInt(id));
    }

    @GetMapping
    public List<Factura> getAllFacturas() {
        // return null;
        return this.facturaService.getAll();
    }

    @PutMapping("/{id}")
    public void updateFactura(@PathVariable int id, @RequestBody UpdateFacturaDto factura) throws Exception {
        this.facturaService.updateFactura(id, factura);
    }

    @DeleteMapping("/{id}")
    public void deleteFactura(@PathVariable String id) throws Exception {
        this.facturaService.deleteFactura(Integer.parseInt(id));
    }

    @GetMapping("/{id}/detalles")
    public Iterable<DetalleFactura> getDetallesFactura(@PathVariable String id) {
        return this.facturaService.getDetallesFactura(Integer.parseInt(id));
    }

}
