package ufps.springBoot.compraservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufps.springBoot.compraservice.entities.Factura;
import ufps.springBoot.compraservice.repository.FacturaRepository;
import ufps.springBoot.compraservice.repository.ItemFacturaRepository;

import java.util.List;

@Service
public class FacturaServiceImpl implements FacturaService{

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private ItemFacturaRepository itemRepository;



    @Override
    public List<Factura> findFacturaAll() {
        return facturaRepository.findAll();
    }

    @Override
    public Factura crearFactura(Factura factura) {
        Factura facturaDB = facturaRepository.findByNumeroFactura(factura.getNumeroFactura ());
        if (facturaDB !=null){
            return  facturaDB;
        }
        factura.setEstado("CREADO");
        return facturaRepository.save(factura);
    }

    @Override
    public Factura actualizarFactura(Factura factura) {
        Factura facturaDB = getFactura(factura.getId());
        if (facturaDB == null){
            return  null;
        }
        facturaDB.setClienteId(factura.getClienteId());
        facturaDB.setDescripcion(factura.getDescripcion());
        facturaDB.setNumeroFactura(factura.getNumeroFactura());
        facturaDB.getItemFactura().clear();
        facturaDB.setItemFactura(factura.getItemFactura());
        return facturaRepository.save(facturaDB);
    }

    @Override
    public Factura eliminarFactura(Factura factura) {
        Factura facturaDB = getFactura(factura.getId());
        if (facturaDB == null){
            return  null;
        }
        facturaDB.setEstado("ELIMINADO");
        return facturaRepository.save(facturaDB);
    }

    @Override
    public Factura getFactura(Long id) {
        return facturaRepository.findById(id).orElse(null);
    }
}
