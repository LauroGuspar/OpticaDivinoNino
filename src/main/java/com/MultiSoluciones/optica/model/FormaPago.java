package com.MultiSoluciones.optica.model;

import jakarta.persistence.*;

@Entity
@Table(name = "forma_pago")
public class FormaPago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fpago")
    private Long id;

    @Column(name = "fpago_nombre")
    private String nombre;

    @Column(name = "fpago_estado")
    private Integer estado = 1;

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

    public FormaPago(String nombre) {
        this.nombre = nombre;
    }

    public FormaPago() {
    }
}
