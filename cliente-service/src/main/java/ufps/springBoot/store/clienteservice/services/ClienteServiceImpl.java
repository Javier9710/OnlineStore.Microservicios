package ufps.springBoot.store.clienteservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufps.springBoot.store.clienteservice.Entities.Cliente;
import ufps.springBoot.store.clienteservice.Entities.Region;
import ufps.springBoot.store.clienteservice.repository.ClienteRepository;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public List<Cliente> listClientesAll() {
        return clienteRepository.findAll();
    }

    @Override
    public List<Cliente> findClientesByRegion(Region region) {
        return clienteRepository.findByRegion(region);
    }

    @Override
    public Cliente crearCliente(Cliente cliente) {
        Cliente clienteDB = clienteRepository.findByDocumento(cliente.getDocumento());
        if (clienteDB != null){
            return clienteDB;
        }
        cliente.setEstado("CREADO");
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente actualizarCliente(Cliente cliente) {
        Cliente clienteDB = this.obtenerCliente(cliente.getId());
        if (clienteDB == null){
            return null;
        }
        clienteDB.setNombre(cliente.getNombre());
        clienteDB.setApellido(cliente.getApellido());
        clienteDB.setEmail(cliente.getEmail());
        clienteDB.setFotoUrl(cliente.getFotoUrl());

        return clienteRepository.save(clienteDB);
    }

    @Override
    public Cliente eliminarCliente(Cliente cliente) {
        Cliente clienteDB = this.obtenerCliente(cliente.getId());
        if (clienteDB == null){
            return null;
        }
        clienteDB.setEstado("ELIMINADO");
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente obtenerCliente(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }
}
