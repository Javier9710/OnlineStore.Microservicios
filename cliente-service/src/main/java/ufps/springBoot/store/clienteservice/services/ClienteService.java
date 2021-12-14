package ufps.springBoot.store.clienteservice.services;

import jdk.dynalink.linker.LinkerServices;
import org.springframework.stereotype.Service;
import ufps.springBoot.store.clienteservice.Entities.Cliente;
import ufps.springBoot.store.clienteservice.Entities.Region;

import java.util.List;

public interface ClienteService {

    public List<Cliente> listClientesAll();
    public List<Cliente> findClientesByRegion(Region region);
    public Cliente crearCliente(Cliente cliente);
    public Cliente actualizarCliente(Cliente cliente);
    public Cliente eliminarCliente(Cliente cliente);
    public Cliente obtenerCliente(Long id);

}
