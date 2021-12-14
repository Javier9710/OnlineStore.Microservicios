package ufps.springBoot.app.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufps.springBoot.app.product.entities.Categoria;
import ufps.springBoot.app.product.entities.Producto;

import java.util.List;

public interface ProductoRepository extends JpaRepository <Producto, Long> {

    public List<Producto> findByCategoria(Categoria categoria);
}
