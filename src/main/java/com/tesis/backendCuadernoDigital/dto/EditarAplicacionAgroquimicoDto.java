package com.tesis.backendCuadernoDigital.dto;

import javax.validation.constraints.NotBlank;

public class EditarAplicacionAgroquimicoDto {
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
}
