package ufps.springBoot.store.clienteservice.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "clientes")
@Data
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "El numero del documento no puede ser vacio")
    @Size(min = 10, max = 10, message = "El documento debe tener 10 numeros")
    @Column(unique = true, length = 10, nullable = false, name = "documento")
    private String documento;
    @NotNull(message = "El campo no puede ser vacio")
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @NotNull(message = "El campo no puede ser vacio")
    @Column(name = "apellido", nullable = false)
    private String apellido;
    @NotEmpty(message = "El correo no puede ser vacio")
    @Email(message = "No es una direccion de correo correcta")
    @Column(unique = true, nullable = false)
    private String email;
    @Column(name = "foto_url")
    private String fotoUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "La region no puede ser vacia")
    @JoinColumn(name = "region")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Region region;

    private String estado;


}
