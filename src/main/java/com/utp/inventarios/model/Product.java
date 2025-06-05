package com.utp.inventarios.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int stock;
    private int stockMinimo;  // Nivel mínimo de stock para alertas
    private double price;

    // Constructor vacío
    public Product() {}

    // Constructor con parámetros
    public Product(String name, int stock, int stockMinimo, double price) {
        this.name = name;
        this.stock = stock;
        this.stockMinimo = stockMinimo;
        this.price = price;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Método toString para una representación amigable del objeto
    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", stock=" + stock + ", stockMinimo=" + stockMinimo + ", price=" + price + "]";
    }
}
