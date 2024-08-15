package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.naming.NameNotFoundException;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.DetalleFacturaDao;
import com.example.demo.dao.FacturaDao;
import com.example.demo.domain.DetalleFactura;
import com.example.demo.domain.Factura;
import com.example.demo.dtos.CreateDetalleFacturaDto;
import com.example.demo.dtos.CreateFacturaDto;
import com.example.demo.dtos.UpdateDetalleFacturaDto;
import com.example.demo.dtos.UpdateFacturaDto;

@Service
public class FacturaService {

    private final FacturaDao facturaDao;

    private final DetalleFacturaDao detalleFacturaDao;

    private final double IVA_PORCENTAGE = 0.12;

    public FacturaService(FacturaDao facturaDao, DetalleFacturaDao detalleFacturaDao) {
        this.facturaDao = facturaDao;
        this.detalleFacturaDao = detalleFacturaDao;
    }

    @Transactional
    public void createFactura(CreateFacturaDto facturaDto) throws Exception {

        try {
            this.findByNumDFactura(facturaDto.getNumero_factura());
        } catch (Exception e) {
            double subtotal = calculateSubtotal(facturaDto.getDetalles());
            double iva = calculateIva(subtotal);
            double total = calculateTotal(subtotal, iva);
            Factura factura = Factura.builder()
                    .numero_factura(facturaDto.getNumero_factura())
                    .id_cliente(facturaDto.getId_cliente())
                    .fecha(facturaDto.getFecha())
                    .subtotal(subtotal)
                    .total(total)
                    .iva(iva)
                    .build();
            facturaDao.create(factura);

            Factura faturaSaved = this.facturaDao.findByNumFactura(facturaDto.getNumero_factura()).get();

            for (CreateDetalleFacturaDto detalle : facturaDto.getDetalles()) {
                DetalleFactura detalleFactura = DetalleFactura.builder()
                        .id_factura(faturaSaved.getId_factura())
                        .id_articulo(detalle.getId_articulo())
                        .cantidad(detalle.getCantidad())
                        .precio_unitario(detalle.getPrecio_unitario())
                        .build();
                detalleFacturaDao.create(detalleFactura);
            }
            return;
        }
        throw new BadRequestException("Factura ya existe");
    }

    double calculateSubtotal(List<CreateDetalleFacturaDto> detalles) {
        double subtotal = 0;
        for (CreateDetalleFacturaDto detalle : detalles) {
            subtotal += detalle.getTotal();
        }
        return subtotal;
    }

    Factura findByNumDFactura(String numFactura) throws NameNotFoundException {
        Optional<Factura> factura = facturaDao.findByNumFactura(numFactura);
        if (factura.isEmpty()) {
            throw new NameNotFoundException("Factura no encontrada");
        }
        return factura.get();
    }

    double calculateIva(double subtotal) {
        return subtotal * IVA_PORCENTAGE;
    }

    double calculateTotal(double subtotal, double iva) {
        return subtotal + iva;
    }

    public List<Factura> getAll() {
        return facturaDao.getAll();
    }

    public Factura getFactura(int id) throws NameNotFoundException {
        Optional<Factura> factura = this.facturaDao.getById(id);

        if (factura.isEmpty()) {
            throw new NameNotFoundException("Factura no encontrada");
        }
        return factura.get();

    }

    public Iterable<DetalleFactura> getDetallesFactura(int id) {
        return detalleFacturaDao.findByFacturaId(id);
    }

    @Transactional
    public void updateFactura(int id, UpdateFacturaDto facturaDto) throws NameNotFoundException {
        Factura factura = this.getFactura(id);
        Iterable<DetalleFactura> detalles = this.getDetallesFactura(factura.getId_factura());

        List<DetalleFactura> detallesToDelete = new ArrayList<>();
        List<DetalleFactura> detallesToUpdate = new ArrayList<>();
        for (DetalleFactura detalle : detalles) {
            boolean found = false;
            for (UpdateDetalleFacturaDto detalleDto : facturaDto.getDetalles()) {
                if (detalle.getId_detalle() == detalleDto.getId_detalle()) {
                    found = true;
                    DetalleFactura detalleFactura = DetalleFactura.builder()
                            .id_detalle(detalle.getId_detalle())
                            .id_factura(detalle.getId_factura())
                            .id_articulo(detalleDto.getId_articulo())
                            .cantidad(detalleDto.getCantidad())
                            .precio_unitario(detalleDto.getPrecio_unitario())
                            .build();
                    detallesToUpdate.add(detalleFactura);
                    break;
                }
            }
            if (!found) {
                detallesToDelete.add(detalle);
            }
        }

        for (DetalleFactura detalle : detallesToDelete) {
            detalleFacturaDao.delete(detalle.getId_detalle());
        }

        for (DetalleFactura detalle : detallesToUpdate) {
            detalleFacturaDao.update(detalle);
        }
        Iterable<DetalleFactura> detallesAfterUpdate = this.getDetallesFactura(factura.getId_factura());

        double subtotal = calculateSubtotalFromDetalles(detallesAfterUpdate);
        double iva = calculateIva(subtotal);
        double total = calculateTotal(subtotal, iva);

        Factura facturaUpdated = Factura.builder()
                .id_factura(factura.getId_factura())
                .numero_factura(facturaDto.getNumero_factura())
                .id_cliente(facturaDto.getId_cliente())
                .fecha(facturaDto.getFecha())
                .subtotal(subtotal)
                .iva(iva)
                .total(total)
                .build();
        facturaDao.update(facturaUpdated);
    }

    public void deleteFactura(int id) throws NameNotFoundException {
        this.getFactura(id);
        facturaDao.delete(id);
    }

    private double calculateSubtotalFromDetalles(Iterable<DetalleFactura> detalles) {
        double subtotal = 0;
        for (DetalleFactura detalle : detalles) {
            subtotal += detalle.getValor();
        }
        return subtotal;
    }

}
