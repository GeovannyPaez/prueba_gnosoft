package com.example.demo.dao.impl;

import com.example.demo.dao.FacturaDao;
import com.example.demo.domain.Factura;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class FacturaDaoImpl implements FacturaDao {

    private final JdbcTemplate jdbcTemplate;

    public FacturaDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Factura> FACTURA_ROW_MAPPER = (rs, rowNum) -> Factura.builder()
            .id_factura(rs.getInt("id_factura"))
            .numero_factura(rs.getString("numero_factura"))
            .id_cliente(rs.getInt("id_cliente"))
            .fecha(rs.getString("fecha"))
            .subtotal(rs.getDouble("subtotal"))
            .iva(rs.getDouble("iva"))
            .total(rs.getDouble("total"))
            .build();

    @Override
    public void create(Factura factura) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fecha = dateFormat.parse(factura.getFecha());
            String sql = "INSERT INTO facturas (numero_factura, id_cliente, fecha, subtotal, iva, total) VALUES (?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, factura.getNumero_factura(), factura.getId_cliente(),
                    fecha,
                    factura.getSubtotal(), factura.getIva(), factura.getTotal());
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @Override
    public Optional<Factura> getById(int id) {
        String sql = "SELECT * FROM facturas WHERE id_factura = ?";
        List<Factura> facturas = jdbcTemplate.query(sql, FACTURA_ROW_MAPPER, id);
        return facturas.isEmpty() ? Optional.empty() : Optional.of(facturas.get(0));
    }

    @Override
    public List<Factura> getAll() {
        String sql = "SELECT * FROM facturas";
        return jdbcTemplate.query(sql, FACTURA_ROW_MAPPER);
    }

    @Override
    public void update(Factura factura) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fecha = dateFormat.parse(factura.getFecha());
            String sql = "UPDATE facturas SET numero_factura = ?, id_cliente = ?, fecha = ?, subtotal = ?, iva = ?, total = ? WHERE id_factura = ?";
            jdbcTemplate.update(sql, factura.getNumero_factura(), factura.getId_cliente(), fecha, factura.getSubtotal(),
                    factura.getIva(), factura.getTotal(), factura.getId_factura());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM facturas WHERE id_factura = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Factura> findByNumFactura(String numFactura) {
        String sql = "SELECT * FROM facturas WHERE numero_factura = ?";
        List<Factura> facturas = jdbcTemplate.query(sql, FACTURA_ROW_MAPPER, numFactura);
        return facturas.isEmpty() ? Optional.empty() : Optional.of(facturas.get(0));
    }

}
