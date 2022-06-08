package com.tesis.backendCuadernoDigital.dto;

import javax.validation.constraints.NotBlank;

public class AplicacionAgroquimicoDto {
    @NotBlank
    private long id;
    @NotBlank
    private String nombreComunAgroquimico;
    @NotBlank
    private String aplicacion;
    @NotBlank
    private int numeroCuadro;
    @NotBlank
    private String observaciones;
    @NotBlank
    private String nombrePlaga;
    @NotBlank
    private String nombreEncargadoAgricola;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombreComunAgroquimico() {
        return nombreComunAgroquimico;
    }

    public void setNombreComunAgroquimico(String nombreComunAgroquimico) {
        this.nombreComunAgroquimico = nombreComunAgroquimico;
    }

    public String getAplicacion() {
        return aplicacion;
    }

    public void setAplicacion(String aplicacion) {
        this.aplicacion = aplicacion;
    }

    public int getNumeroCuadro() {
        return numeroCuadro;
    }

    public void setNumeroCuadro(int numeroCuadro) {
        this.numeroCuadro = numeroCuadro;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getNombrePlaga() {
        return nombrePlaga;
    }

    public void setNombrePlaga(String nombrePlaga) {
        this.nombrePlaga = nombrePlaga;
    }

    public String getNombreEncargadoAgricola() {
        return nombreEncargadoAgricola;
    }

    public void setNombreEncargadoAgricola(String nombreEncargadoAgricola) {
        this.nombreEncargadoAgricola = nombreEncargadoAgricola;
    }
}
