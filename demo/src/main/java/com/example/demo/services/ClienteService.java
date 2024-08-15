package com.example.demo.services;

import java.util.Optional;

import javax.naming.NameNotFoundException;

import org.springframework.stereotype.Service;

import com.example.demo.dao.ClientDao;
import com.example.demo.domain.Cliente;

@Service
public class ClienteService {
    private ClientDao clientDao;

    public ClienteService(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public void createCliente(Cliente cliente) {
        clientDao.create(cliente);
    }

    public Iterable<Cliente> getAllClientes() {
        return clientDao.getAll();
    }

    public Cliente getClienteById(int id) throws NameNotFoundException {
        Optional<Cliente> cliente = clientDao.getById(id);

        if (cliente.isEmpty()) {
            throw new NameNotFoundException("Cliente no encontrado");
        }

        return cliente.get();
    }

    public void updateCliente(Cliente cliente) throws NameNotFoundException {
        this.getClienteById(cliente.getId_client());
        clientDao.update(cliente);
    }

    public void deleteCliente(int id) {
        clientDao.delete(id);
    }

}
