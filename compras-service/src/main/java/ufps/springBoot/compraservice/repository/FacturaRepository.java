package ufps.springBoot.compraservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufps.springBoot.compraservice.entities.Factura;

import java.util.List;

public interface FacturaRepository extends JpaRepository<Factura, Long> {

    public List<Factura> findByClienteId(Long id);
    public Factura findByNumeroFactura(String numeroFactura);
}
