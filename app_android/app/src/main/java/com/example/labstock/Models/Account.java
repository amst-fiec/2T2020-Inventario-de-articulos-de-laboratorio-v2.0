package com.example.labstock.Models;
// Profile.java

import com.fasterxml.jackson.annotation.*;

public class Account {
    private String correo;
    private Laboratorio[] laboratorios;

    @JsonProperty("correo")
    public String getCorreo() { return correo; }
    @JsonProperty("correo")
    public void setCorreo(String value) { this.correo = value; }

    @JsonProperty("laboratorios")
    public Laboratorio[] getLaboratorios() { return laboratorios; }
    @JsonProperty("laboratorios")
    public void setLaboratorios(Laboratorio[] value) { this.laboratorios = value; }
}