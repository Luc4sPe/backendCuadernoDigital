package com.tesis.backendCuadernoDigital.dto;

import javax.validation.constraints.NotBlank;

public class PlagaDto {

    @NotBlank
    private long id;
    @NotBlank
    private String nombre;
    @NotBlank
    private String descripcion;
    @NotBlank
    private String nombreEncargadoAgricola;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreEncargadoAgricola() {
        return nombreEncargadoAgricola;
    }

    public void setNombreEncargadoAgricola(String nombreEncargadoAgricola) {
        this.nombreEncargadoAgricola = nombreEncargadoAgricola;
    }
}
