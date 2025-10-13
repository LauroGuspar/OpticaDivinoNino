package com.MultiSoluciones.optica.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tipo_venta")
public class TipoVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipoventa")
    private Long id;

    @Column(name = "tipoventa_nombre")
    private String nombre;

    @Column(name = "tipoventa_estado")
    private Integer estado = 1;

    public TipoVenta() {
    }

    public TipoVenta(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }
}
