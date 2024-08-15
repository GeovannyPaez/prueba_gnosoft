package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T> {

    // Crear un nuevo cliente
    void create(T t);

    // Obtener un cliente por ID
    Optional<T> getById(int id);

    // Obtener todos los clientes
    List<T> getAll();

    // Actualizar un cliente
    void update(T t);

    // Eliminar un cliente por ID
    void delete(int id);
}
