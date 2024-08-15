package com.example.demo.dao.impl;

import com.example.demo.dao.DetalleFacturaDao;
import com.example.demo.domain.DetalleFactura;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DetalleFacturaDaoImpl implements DetalleFacturaDao {

    private final JdbcTemplate jdbcTemplate;

    public DetalleFacturaDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<DetalleFactura> DETALLE_FACTURA_ROW_MAPPER = (rs, rowNum) -> DetalleFactura.builder()
            .id_detalle(rs.getInt("id_detalle"))
            .id_factura(rs.getInt("id_factura"))
            .id_articulo(rs.getInt("id_articulo"))
            .cantidad(rs.getInt("cantidad"))
            .precio_unitario(rs.getDouble("precio_unitario"))
            .valor(rs.getDouble("valor"))
            .build();

    @Override
    public void create(DetalleFactura detalleFactura) {
        String sql = "INSERT INTO detalles_factura (id_factura, id_articulo, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, detalleFactura.getId_factura(), detalleFactura.getId_articulo(),
                detalleFactura.getCantidad(), detalleFactura.getPrecio_unitario());
    }

    @Override
    public Optional<DetalleFactura> getById(int id) {
        String sql = "SELECT * FROM detalles_factura WHERE id_detalle = ?";
        List<DetalleFactura> detalles = jdbcTemplate.query(sql, DETALLE_FACTURA_ROW_MAPPER, id);
        return detalles.isEmpty() ? Optional.empty() : Optional.of(detalles.get(0));
    }

    @Override
    public List<DetalleFactura> getAll() {
        String sql = "SELECT * FROM detalles_factura";
        return jdbcTemplate.query(sql, DETALLE_FACTURA_ROW_MAPPER);
    }

    @Override
    public void update(DetalleFactura detalleFactura) {
        String sql = "UPDATE detalles_factura SET id_factura = ?, id_articulo = ?, cantidad = ?, precio_unitario = ? WHERE id_detalle = ?";
        jdbcTemplate.update(sql, detalleFactura.getId_factura(), detalleFactura.getId_articulo(),
                detalleFactura.getCantidad(), detalleFactura.getPrecio_unitario(), detalleFactura.getId_detalle());
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM detalles_factura WHERE id_detalle = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<DetalleFactura> findByFacturaId(int id_factura) {
        String sql = "SELECT * FROM detalles_factura WHERE id_factura = ?";
        return jdbcTemplate.query(sql, DETALLE_FACTURA_ROW_MAPPER, id_factura);
    }

    @Override
    public double getSubtotalFactura(int id_factura) {
        String sql = "SELECT SUM(cantidad * precio_unitario) as subtotal FROM detalles_factura WHERE id_factura = ?";
        return jdbcTemplate.queryForObject(sql, Double.class, id_factura);
    }

}
