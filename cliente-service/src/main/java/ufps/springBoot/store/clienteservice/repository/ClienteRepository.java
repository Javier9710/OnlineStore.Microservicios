package ufps.springBoot.store.clienteservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufps.springBoot.store.clienteservice.Entities.Cliente;
import ufps.springBoot.store.clienteservice.Entities.Region;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {

    public Cliente findByDocumento(String documento);
    public List<Cliente> findByApellido(String apellido);
    public List<Cliente> findByRegion(Region regionId);
}
