    package com.example.pedilo_ya.entities.Factura.DetalleFactura;

    import jakarta.persistence.*;
    import org.hibernate.annotations.CreationTimestamp;

    import java.util.Date;

    @Entity
    @Table(name = "detalles_factura")
    public class DetalleFactura {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
        @Column(name = "detalle")
        private String detalleFactura;
        @Column(name = "tipoFactura")
        private TipoFactura tipoFactura;
        @Column(name = "fecha", updatable = false, nullable = false)
        @Temporal(TemporalType.TIMESTAMP)
        @CreationTimestamp
        public Date fechaFactura;

        public DetalleFactura() {
        }

        public DetalleFactura(String detalleFactura, TipoFactura tipoFactura) {
            this.detalleFactura = detalleFactura;
            this.tipoFactura = tipoFactura;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getDetalleFactura() {
            return detalleFactura;
        }

        public void setDetalleFactura(String detalleFactura) {
            this.detalleFactura = detalleFactura;
        }

        public TipoFactura getTipoFactura() {
            return tipoFactura;
        }

        public void setTipoFactura(TipoFactura tipoFactura) {
            this.tipoFactura = tipoFactura;
        }

        public Date getFechaFactura() {
            return fechaFactura;
        }

        public void setFechaFactura(Date fechaFactura) {
            this.fechaFactura = fechaFactura;
        }
    }