package ufps.springBoot.store.clienteservice.controllers;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter @Setter @Builder
public class ErrorMensaje {

    private String codigo;
    private List<Map<String, String>> mensajes;

}
