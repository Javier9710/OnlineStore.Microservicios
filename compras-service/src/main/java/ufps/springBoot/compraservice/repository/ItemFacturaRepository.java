package ufps.springBoot.compraservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufps.springBoot.compraservice.entities.ItemFactura;

import java.util.List;

public interface ItemFacturaRepository extends JpaRepository<ItemFactura, Long> {


}
