package com.MultiSoluciones.optica.model;

import jakarta.persistence.*;

@Entity
@Table(name = "inventario")
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inventario")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @Column(name = "inventario_stock")
    private Integer stock;

    @Column(name = "inventario_stock_minimo")
    private Integer stockMinimo;

    public Inventario() {
    }

    public Inventario(Producto producto, Integer stock, Integer stockMinimo) {
        this.producto = producto;
        this.stock = stock;
        this.stockMinimo = stockMinimo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(Integer stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    @Override
    public String toString() {
        return "Inventario{" +
                "id=" + id +
                ", producto=" + producto +
                ", stock=" + stock +
                ", stockMinimo=" + stockMinimo +
                '}';
    }
}
