package ufps.springBoot.app.product;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ufps.springBoot.app.product.entities.Categoria;
import ufps.springBoot.app.product.entities.Producto;
import ufps.springBoot.app.product.repository.ProductoRepository;

import java.util.Date;
import java.util.List;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductoRepositoryMockTest {

    @Autowired
    private ProductoRepository productoRepository;

    @Test
    public void buscarCategoria_retornaListaProd(){

        Producto prodcuto01 = Producto.builder()
                .nombre("computador")
                .categoria(Categoria.builder().id(1L).build())
                .descripcion("")
                .precio(Double.parseDouble("10000"))
                .stock(Double.parseDouble("10"))
                .estado("creado")
                .creteAt(new Date()).build();

        productoRepository.save(prodcuto01);

        List<Producto> productos = productoRepository.findByCategoria(prodcuto01.getCategoria());

        Assertions.assertThat(productos.size()).isEqualTo(3);


    }
}
