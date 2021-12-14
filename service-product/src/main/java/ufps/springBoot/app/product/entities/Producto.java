package ufps.springBoot.app.product.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

@Entity
@Table(name = "productos")
@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "El campo no debe ser vacio")
    private String nombre;
    private String descripcion;
    @Positive(message = "Debe ser un valor positivo")
    private Double stock;
    private Double precio;
    private String estado;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date creteAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @NotNull(message = "La categoria no puede ser vacia")
    private Categoria categoria;
}
