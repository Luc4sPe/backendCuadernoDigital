package com.tesis.backendCuadernoDigital.dto;

import javax.validation.constraints.NotBlank;

public class EditarAgroquimico {

    @NotBlank
    private String formulaYconcentracion;
    @NotBlank
    private String lote;
    @NotBlank
    private String nombreComun;
    @NotBlank
    private String observaciones;
    @NotBlank
    private String nombrePlaga;
    @NotBlank
    private String principioActivo;
    @NotBlank
    private int tiempoDeCarencia;
    @NotBlank
    private String nombreTipoAgroquimico;

    public String getFormulaYconcentracion() {
        return formulaYconcentracion;
    }

    public void setFormulaYconcentracion(String formulaYconcentracion) {
        this.formulaYconcentracion = formulaYconcentracion;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getNombreComun() {
        return nombreComun;
    }

    public void setNombreComun(String nombreComun) {
        this.nombreComun = nombreComun;
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

    public String getPrincipioActivo() {
        return principioActivo;
    }

    public void setPrincipioActivo(String principioActivo) {
        this.principioActivo = principioActivo;
    }

    public int getTiempoDeCarencia() {
        return tiempoDeCarencia;
    }

    public void setTiempoDeCarencia(int tiempoDeCarencia) {
        this.tiempoDeCarencia = tiempoDeCarencia;
    }

    public String getNombreTipoAgroquimico() {
        return nombreTipoAgroquimico;
    }

    public void setNombreTipoAgroquimico(String nombreTipoAgroquimico) {
        this.nombreTipoAgroquimico = nombreTipoAgroquimico;
    }
}
