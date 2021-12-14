package ufps.springBoot.compraservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ufps.springBoot.compraservice.entities.Factura;
import ufps.springBoot.compraservice.services.FacturaService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    // -------------------Retrieve All Invoices--------------------------------------------
    @GetMapping
    public ResponseEntity<List<Factura>> listAllFacturas() {
        List<Factura> facturas = facturaService.findFacturaAll();
        if (facturas.isEmpty()) {
            return  ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok(facturas);
    }

    // -------------------Retrieve Single Invoice------------------------------------------
    @GetMapping(value = "/{id}")
    public ResponseEntity<Factura> getInvoice(@PathVariable("id") long id) {
        log.info("Fetching Invoice with id {}", id);
        Factura factura  = facturaService.getFactura(id);
        if (null == factura) {
            log.error("Invoice with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(factura);
    }

    // -------------------Create a Invoice-------------------------------------------
    @PostMapping
    public ResponseEntity<Factura> crearFactura(@Valid @RequestBody Factura factura, BindingResult result) {
        log.info("Creating Invoice : {}", factura);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatoMensaje(result));
        }
        Factura facturaDB = facturaService.crearFactura(factura);

        return  ResponseEntity.status( HttpStatus.CREATED).body(facturaDB);
    }

    // ------------------- Update a Invoice ------------------------------------------------
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> ActualizarFactura(@PathVariable("id") long id, @RequestBody Factura factura) {
        log.info("Updating Invoice with id {}", id);

        factura.setId(id);
        Factura actualizaFactura=facturaService.actualizarFactura(factura);

        if (actualizaFactura == null) {
            log.error("Unable to update. Invoice with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(actualizaFactura);
    }

    // ------------------- Delete a Invoice-----------------------------------------
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Factura> eliminarFactura(@PathVariable("id") long id) {
        log.info("Fetching & Deleting Invoice with id {}", id);

        Factura factura = facturaService.getFactura(id);
        if (factura == null) {
            log.error("Unable to delete. Invoice with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        factura = facturaService.eliminarFactura(factura);
        return ResponseEntity.ok(factura);
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
