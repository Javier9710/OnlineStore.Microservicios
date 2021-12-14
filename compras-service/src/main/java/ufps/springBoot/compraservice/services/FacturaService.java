package ufps.springBoot.compraservice.services;

import org.springframework.stereotype.Service;
import ufps.springBoot.compraservice.entities.Factura;

import java.util.List;


public interface FacturaService {
    public List<Factura> findFacturaAll();

    public Factura crearFactura(Factura factura);
    public Factura actualizarFactura(Factura factura);
    public Factura eliminarFactura(Factura factura);

    public Factura getFactura(Long id);
}
