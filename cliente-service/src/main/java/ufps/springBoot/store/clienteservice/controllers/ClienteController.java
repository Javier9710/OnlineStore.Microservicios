package ufps.springBoot.store.clienteservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ufps.springBoot.store.clienteservice.Entities.Cliente;
import ufps.springBoot.store.clienteservice.Entities.Region;
import ufps.springBoot.store.clienteservice.services.ClienteService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@Valid @RequestBody Cliente cliente, BindingResult result){

        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatoMensaje(result));
        }

        Cliente clienteDB = clienteService.crearCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteDB);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable(name = "id") Long id, @RequestBody Cliente cliente){
        Cliente clienteDB = clienteService.obtenerCliente(id);
        if (clienteDB == null){
            log.error("El cliente con id {} no existe", id);
            return ResponseEntity.notFound().build();
        }
        cliente.setId(id);
        clienteDB = clienteService.actualizarCliente(cliente);
        return ResponseEntity.ok(clienteDB);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cliente> eliminarCliente(@PathVariable(name = "id") Long id){
        Cliente clienteDB = clienteService.obtenerCliente(id);
        if (clienteDB == null){
            return  ResponseEntity.notFound().build();
        }

        clienteDB = clienteService.eliminarCliente(clienteDB);
        return ResponseEntity.ok(clienteDB);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> obtenerClientes(@RequestParam (name = "id", required = false) Long id){
        List<Cliente> clientesDB = new ArrayList<>();
        if (id == null){
            clientesDB = clienteService.listClientesAll();
            if (clientesDB.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }else{
            Region region = new Region();
            region.setId(id);
            clientesDB = clienteService.findClientesByRegion(region);
            if (clientesDB == null){
                log.error("Clientes con region id {} no funciona.", id);
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(clientesDB);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerClienteId(@PathVariable (name = "id") Long id){
        log.info("Buscando Cliente con id {}. ", id);
        Cliente clienteDB = clienteService.obtenerCliente(id);
        if (clienteDB == null){
            log.error("Cliente con id {} no existe. " , id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clienteDB);
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
