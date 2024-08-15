package com.example.demo.dao.impl;

import com.example.demo.dao.ClientDao;
import com.example.demo.domain.Cliente;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ClientDaoImpl implements ClientDao {

    private final JdbcTemplate jdbcTemplate;

    public ClientDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Cliente> CLIENTE_ROW_MAPPER = (rs, rowNum) -> Cliente.builder()
            .id_client(rs.getInt("id_cliente"))
            .nombre(rs.getString("nombre"))
            .build();

    @Override
    public void create(Cliente cliente) {
        String sql = "INSERT INTO clientes (nombre) VALUES (?)";
        jdbcTemplate.update(sql, cliente.getNombre());
    }

    @Override
    public Optional<Cliente> getById(int id) {
        String sql = "SELECT * FROM clientes WHERE id_cliente = ?";
        List<Cliente> clientes = jdbcTemplate.query(sql, CLIENTE_ROW_MAPPER, id);
        return clientes.isEmpty() ? Optional.empty() : Optional.of(clientes.get(0));
    }

    @Override
    public List<Cliente> getAll() {
        String sql = "SELECT * FROM clientes";
        return jdbcTemplate.query(sql, CLIENTE_ROW_MAPPER);
    }

    @Override
    public void update(Cliente cliente) {
        String sql = "UPDATE clientes SET nombre = ? WHERE id_cliente = ?";
        jdbcTemplate.update(sql, cliente.getNombre(), cliente.getId_client());
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM clientes WHERE id_cliente = ?";
        jdbcTemplate.update(sql, id);
    }
}
