package ufps.springBoot.app.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ufps.springBoot.app.product.entities.Categoria;
import ufps.springBoot.app.product.entities.Producto;
import ufps.springBoot.app.product.repository.ProductoRepository;
import ufps.springBoot.app.product.service.ProdcutoServoceImpl;
import ufps.springBoot.app.product.service.ProductoService;

import java.util.Optional;

@SpringBootTest
public class ProductoServiceMockTest {

    @Mock
    private ProductoRepository productoRepository;

    private ProductoService productoService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        productoService = new ProdcutoServoceImpl(productoRepository);
        Producto computador = Producto.builder()
                .id(1L)
                .nombre("Computador")
                .categoria(Categoria.builder().id(1L).build())
                .precio(Double.parseDouble("12.5"))
                .stock(Double.parseDouble("5"))
                .build();

        Mockito.when(productoRepository.findById(1L))
                .thenReturn(Optional.of(computador));

        Mockito.when(productoRepository.save(computador)).thenReturn(computador);

    }

    @Test
    public void validarId_retornaProdcuto(){
        Producto found = productoService.getProducto(1L);
        Assertions.assertThat(found.getNombre()).isEqualTo("Computador");

    }

    @Test
    public void validarStock_retornaStockActualizado(){
        Producto nuevoStock = productoService.updateStock(1L, Double.parseDouble("8"));
        Assertions.assertThat(nuevoStock.getStock()).isEqualTo(13);
    }
}
