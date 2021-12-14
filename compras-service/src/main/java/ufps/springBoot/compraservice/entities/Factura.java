package ufps.springBoot.compraservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "facturas")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_factura")
    private String numeroFactura;

    private String descripcion;

    @Column(name = "cliente_id")
    private Long clienteId;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;

    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    @Valid
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "item_factura")
    private List<ItemFactura> itemFactura;

    private String estado;

    @PrePersist
    public void prePersist(){
        this.fechaCreacion=new Date();
    }
}
