package ufps.springBoot.app.product.service;

import ufps.springBoot.app.product.entities.Categoria;
import ufps.springBoot.app.product.entities.Producto;

import java.util.List;

public interface ProductoService {

    public List<Producto> listAllProductos();
    public Producto getProducto(Long id);

    public Producto saveProducto(Producto producto);
    public Producto updateProducto(Producto producto);
    public Producto deleteProduct(Long id);
    public List<Producto> findByCategoria(Categoria categoria);
    public Producto updateStock(Long id, Double cantidad);

}
