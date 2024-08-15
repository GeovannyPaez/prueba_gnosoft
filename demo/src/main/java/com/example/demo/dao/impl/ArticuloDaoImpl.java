package com.example.demo.dao.impl;

import com.example.demo.dao.ArticuloDao;
import com.example.demo.domain.Articulo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ArticuloDaoImpl implements ArticuloDao {

    private final JdbcTemplate jdbcTemplate;

    public ArticuloDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Articulo> ARTICULO_ROW_MAPPER = (rs, rowNum) -> Articulo.builder()
            .id_articulo(rs.getInt("id_articulo"))
            .nombre(rs.getString("nombre"))
            .precio(rs.getDouble("precio"))
            .build();

    @Override
    public void create(Articulo articulo) {
        String sql = "INSERT INTO articulos (nombre, precio) VALUES (?, ?)";
        jdbcTemplate.update(sql, articulo.getNombre(), articulo.getPrecio());
    }

    @Override
    public Optional<Articulo> getById(int id) {
        String sql = "SELECT * FROM articulos WHERE id_articulo = ?";
        List<Articulo> articulos = jdbcTemplate.query(sql, ARTICULO_ROW_MAPPER, id);
        return articulos.isEmpty() ? Optional.empty() : Optional.of(articulos.get(0));
    }

    @Override
    public List<Articulo> getAll() {
        String sql = "SELECT * FROM articulos";
        return jdbcTemplate.query(sql, ARTICULO_ROW_MAPPER);
    }

    @Override
    public void update(Articulo articulo) {
        String sql = "UPDATE articulos SET nombre = ?, precio = ? WHERE id_articulo = ?";
        jdbcTemplate.update(sql, articulo.getNombre(), articulo.getPrecio(), articulo.getId_articulo());
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM articulos WHERE id_articulo = ?";
        jdbcTemplate.update(sql, id);
    }
}
