package com.MultiSoluciones.optica.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ajustesfront")
public class AjustesFront {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_ajustesfront", nullable=false)
    private Long id;

    @Column(name="af_nombre")
    private String nombre;

    @Column(name="af_loginbg")
    private String loginBg;


}
