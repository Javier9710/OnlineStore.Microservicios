package ufps.springBoot.app.product.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ufps.springBoot.app.product.entities.Categoria;
import ufps.springBoot.app.product.entities.Producto;
import ufps.springBoot.app.product.service.ProductoService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> listProdcuto(@RequestParam(name = "id", required = false) Long categoriaId) {
        List<Producto> productos = productoService.listAllProductos();
        if (categoriaId == null) {
            productos = productoService.listAllProductos();
            if (productos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
        } else {
            productos = productoService.findByCategoria(Categoria.builder().id(categoriaId).build());
            if (productos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
        }

        return ResponseEntity.ok(productos);
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable(name = "id", required = true) Long id) {
        Producto producto = productoService.getProducto(id);
        if (producto == null) {
            return ResponseEntity.notFound().build();

        }
        return ResponseEntity.ok(producto);
    }

    @PostMapping
    public ResponseEntity<Producto> crearProducto(@Valid @RequestBody Producto producto, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatoMensaje(result));


        }

        Producto productoCreado = productoService.saveProducto(producto);

        return ResponseEntity.status(HttpStatus.CREATED).body(productoCreado);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable(name = "id") Long id, @RequestBody Producto producto) {

        producto.setId(id);
        Producto productoDB = productoService.updateProducto(producto);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(productoDB);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Producto> eliminarProducto(@PathVariable("id") Long id) {
        Producto productoDelete = productoService.deleteProduct(id);

        if (productoDelete == null) {
            return ResponseEntity.notFound().build();

        }
        return ResponseEntity.ok(productoDelete);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> actualizarStock(@PathVariable("id") Long id, @RequestParam(value = "agregado", required = true) Double agrgado) {
        Producto producto = productoService.updateStock(id, agrgado);
        if (producto == null) {
            return ResponseEntity.notFound().build();

        }
        return ResponseEntity.ok(producto);
    }

    private String formatoMensaje(BindingResult result) {
        List<Map<String, String>> errores = result.getFieldErrors().stream()
                .map(err ->
                {
                    Map<String, String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());

        ErrorMensaje errorMensaje = ErrorMensaje.builder().codigo("01").mensajes(errores).build();

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";

        try {
            jsonString = mapper.writeValueAsString(errorMensaje);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonString;


    }


    }
