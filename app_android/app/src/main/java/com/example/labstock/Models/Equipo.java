package com.example.labstock.Models;
// Equipo.java

import com.fasterxml.jackson.annotation.*;

public class Equipo {
    private String nombre;
    private String marca;
    private String modelo;
    private String descripcion;
    private long estado;
    private String responsable;
    private Prestamo[] prestamos;

    @JsonProperty("nombre")
    public String getNombre() { return nombre; }
    @JsonProperty("nombre")
    public void setNombre(String value) { this.nombre = value; }

    @JsonProperty("marca")
    public String getMarca() { return marca; }
    @JsonProperty("marca")
    public void setMarca(String value) { this.marca = value; }

    @JsonProperty("modelo")
    public String getModelo() { return modelo; }
    @JsonProperty("modelo")
    public void setModelo(String value) { this.modelo = value; }

    @JsonProperty("descripcion")
    public String getDescripcion() { return descripcion; }
    @JsonProperty("descripcion")
    public void setDescripcion(String value) { this.descripcion = value; }

    @JsonProperty("estado")
    public long getEstado() { return estado; }
    @JsonProperty("estado")
    public void setEstado(long value) { this.estado = value; }

    @JsonProperty("responsable")
    public String getResponsable() { return responsable; }
    @JsonProperty("responsable")
    public void setResponsable(String value) { this.responsable = value; }

    @JsonProperty("prestamos")
    public Prestamo[] getPrestamos() { return prestamos; }
    @JsonProperty("prestamos")
    public void setPrestamos(Prestamo[] value) { this.prestamos = value; }
}
