package ufps.springBoot.compraservice.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Entity
@Data
@Table(name = "item_facturas")
public class ItemFactura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Positive(message = "El valor ingresado debe ser mayor a 0")
    private Double cantidad;
    private Double precio;

    @Column(name = "producto_id")
    private Long productoId;

    @Transient
    private Double subTotal;

    public Double getSubTotal(){
        if (this.cantidad>0 && this.precio>0){
            return this.cantidad*this.precio;

        }else
        {
            return (double)0;
        }
    }

    public ItemFactura(){
        this.cantidad=(double) 0;
        this.precio=(double) 0;

    }
}
