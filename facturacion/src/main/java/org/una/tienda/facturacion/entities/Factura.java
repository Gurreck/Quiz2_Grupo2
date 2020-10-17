package org.una.tienda.facturacion.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Adrian
 */

@Entity
@Table(name = "ut_facturas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Factura implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "caja", length = 11)
    private String caja;

    @Column(name = "descuento_general")
    private double descuentoGeneral;

    @Column
    private boolean estado;

    @Column(name = "fecha_modificacion")
    @Setter(AccessLevel.NONE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;

    @Column(name = "fecha_registro", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Setter(AccessLevel.NONE)
    private Date fechaRegistro;

    @ManyToOne
    @JoinColumn(name="cliente_id")
    private Cliente cliente;

   // @OneToMany(cascade = CascadeType.ALL, mappedBy = "factura")
   // private List<FacturaDetalle> facturasDetalles = new ArrayList<>();

    private static final long serialVersionUID = 1L;

    @PrePersist
    public void prePersist() {
        estado=true;
        fechaRegistro = new Date();
        fechaModificacion = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        fechaModificacion = new Date();
    }

}