package org.una.tienda.facturacion.entities;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ut_productos_precios")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductoPrecio implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Double descuento_maximo;

    @Column
    private Double descuento_promocional;

    @Column
    private boolean estado;

    @Column(name = "fecha_registro", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Setter(AccessLevel.NONE)
    private Date fechaRegistro;

    @Column(name = "fecha_modificacion")
    @Setter(AccessLevel.NONE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Column
    private Double precio_colones;
    /*
    @ManyToOne
    @JoinColumn(name="ut_productos_id")
    private Producto producto;
*/
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
