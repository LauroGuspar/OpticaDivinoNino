package com.MultiSoluciones.optica.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "venta")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_fpago")
    private FormaPago formaPago;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tipoventa")
    private TipoVenta tipoVenta;

    @Column(name = "venta_fecha")
    private LocalDateTime fecha;

    @Column(name = "venta_total")
    private BigDecimal total;

    @Column(name = "venta_deuda")
    private BigDecimal deuda;

    @Column(name = "venta_situacion")
    private String situacion;

    @OneToMany(mappedBy = "venta", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("venta")
    private Set<Detalle> detalles = new HashSet<>();

    @Column(name = "estado")
    private Integer estado = 1;

    public Venta() {
    }

    public Venta(Cliente cliente, Usuario usuario, FormaPago formaPago, TipoVenta tipoVenta, LocalDateTime fecha, BigDecimal total, BigDecimal deuda, String situacion, Set<Detalle> detalles) {
        this.cliente = cliente;
        this.usuario = usuario;
        this.formaPago = formaPago;
        this.tipoVenta = tipoVenta;
        this.fecha = fecha;
        this.total = total;
        this.deuda = deuda;
        this.situacion = situacion;
        this.detalles = detalles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public TipoVenta getTipoVenta() {
        return tipoVenta;
    }

    public void setTipoVenta(TipoVenta tipoVenta) {
        this.tipoVenta = tipoVenta;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getSituacion() {
        return situacion;
    }

    public void setSituacion(String situacion) {
        this.situacion = situacion;
    }

    public Set<Detalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(Set<Detalle> detalles) {
        this.detalles = detalles;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public BigDecimal getDeuda() {
        return deuda;
    }

    public void setDeuda(BigDecimal deuda) {
        this.deuda = deuda;
    }
}
