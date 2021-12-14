package ufps.springBoot.app.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufps.springBoot.app.product.entities.Categoria;
import ufps.springBoot.app.product.entities.Producto;
import ufps.springBoot.app.product.repository.ProductoRepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdcutoServoceImpl implements ProductoService {


    private final ProductoRepository productoRepository;

    @Override
    public List<Producto> listAllProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto getProducto(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    public Producto saveProducto(Producto producto) {
        producto.setEstado("Creado");
        producto.setCreteAt(new Date());
       return  productoRepository.save(producto);

    }

    @Override
    public Producto updateProducto(Producto producto) {
        Producto productoDB = getProducto(producto.getId());

        if (productoDB == null){
            return null;
        }
        productoDB.setNombre(producto.getNombre());
        productoDB.setDescripcion(producto.getDescripcion());
        productoDB.setCategoria(producto.getCategoria());
        productoDB.setPrecio(producto.getPrecio());
        return productoRepository.save(producto);
    }

    @Override
    public Producto deleteProduct(Long id) {
        Producto productoDB = getProducto(id);
        if (productoDB == null){
            return null;
        }
        productoDB.setEstado("ELIMINADO");
        return productoRepository.save(productoDB);
    }

    @Override
    public List<Producto> findByCategoria(Categoria categoria) {
        return productoRepository.findByCategoria(categoria);
    }

    @Override
    public Producto updateStock(Long id, Double cantidad) {
        Producto productoDB = getProducto(id);

        if (productoDB == null){
            return null;
        }

        Double stock = productoDB.getStock() + cantidad;
        productoDB.setStock(stock);
        return productoRepository.save(productoDB);
    }
}
