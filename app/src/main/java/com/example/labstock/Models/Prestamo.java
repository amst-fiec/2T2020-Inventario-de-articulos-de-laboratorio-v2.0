package com.example.labstock.Models;
// Prestamo.java

import com.fasterxml.jackson.annotation.*;

public class Prestamo {
    private String fechaPrestamo;
    private String fechaDevolucion;
    private String responsable;
    private String ubicacion;
    private boolean devuelto;

    @JsonProperty("fechaPrestamo")
    public String getFechaPrestamo() { return fechaPrestamo; }
    @JsonProperty("fechaPrestamo")
    public void setFechaPrestamo(String value) { this.fechaPrestamo = value; }

    @JsonProperty("fechaDevolucion")
    public String getFechaDevolucion() { return fechaDevolucion; }
    @JsonProperty("fechaDevolucion")
    public void setFechaDevolucion(String value) { this.fechaDevolucion = value; }

    @JsonProperty("responsable")
    public String getResponsable() { return responsable; }
    @JsonProperty("responsable")
    public void setResponsable(String value) { this.responsable = value; }

    @JsonProperty("ubicacion")
    public String getUbicacion() { return ubicacion; }
    @JsonProperty("ubicacion")
    public void setUbicacion(String value) { this.ubicacion = value; }

    @JsonProperty("devuelto")
    public boolean getDevuelto() { return devuelto; }
    @JsonProperty("devuelto")
    public void setDevuelto(boolean value) { this.devuelto = value; }
}
