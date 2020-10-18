package org.una.tienda.facturacion.entities;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
@Table(name = "ut_productos_existencias")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductoExistencia implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Double cantidad;

    @Column
    private Boolean estado;

    @Column(name = "fecha_registro", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Setter(AccessLevel.NONE)
    private Date fechaRegistro;

    @Column(name = "fecha_modificacion")
    @Setter(AccessLevel.NONE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;

    @ManyToOne
    @JoinColumn(name="ut_productos_id")
    private Producto producto;

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
