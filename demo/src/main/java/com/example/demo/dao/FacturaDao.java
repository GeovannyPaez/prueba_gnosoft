package com.example.demo.dao;

import java.util.Optional;

import com.example.demo.domain.Factura;

public interface FacturaDao extends CrudDao<Factura> {
    public Optional<Factura> findByNumFactura(String numFactura);

}
