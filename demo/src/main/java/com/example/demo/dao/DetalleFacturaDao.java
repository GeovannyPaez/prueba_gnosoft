package com.example.demo.dao;

import com.example.demo.domain.DetalleFactura;

public interface DetalleFacturaDao extends CrudDao<DetalleFactura> {
    Iterable<DetalleFactura> findByFacturaId(int id);

    double getSubtotalFactura(int id);
}
