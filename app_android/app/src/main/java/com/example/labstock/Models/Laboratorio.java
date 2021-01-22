package com.example.labstock.Models;
// Laboratorio.java


import com.fasterxml.jackson.annotation.*;

public class Laboratorio {
    private String nombre;
    private String descripcion;
    private String ubicacion;
    private Equipo[] equipos;

    @JsonProperty("nombre")
    public String getNombre() { return nombre; }
    @JsonProperty("nombre")
    public void setNombre(String value) { this.nombre = value; }

    @JsonProperty("descripcion")
    public String getDescripcion() { return descripcion; }
    @JsonProperty("descripcion")
    public void setDescripcion(String value) { this.descripcion = value; }

    @JsonProperty("ubicacion")
    public String getUbicacion() { return ubicacion; }
    @JsonProperty("ubicacion")
    public void setUbicacion(String value) { this.ubicacion = value; }

    @JsonProperty("equipos")
    public Equipo[] getEquipos() { return equipos; }
    @JsonProperty("equipos")
    public void setEquipos(Equipo[] value) { this.equipos = value; }
}