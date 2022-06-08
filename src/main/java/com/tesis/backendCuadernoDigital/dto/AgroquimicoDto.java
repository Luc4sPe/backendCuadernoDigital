package com.tesis.backendCuadernoDigital.dto;

import com.tesis.backendCuadernoDigital.entity.Plaga;
import com.tesis.backendCuadernoDigital.entity.TipoAgroquimico;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;

import javax.validation.constraints.NotBlank;

public class AgroquimicoDto {
    @NotBlank
    private long id;
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
    @NotBlank
    private String nombreEncargadoAgricola;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String getNombreEncargadoAgricola() {
        return nombreEncargadoAgricola;
    }

    public void setNombreEncargadoAgricola(String nombreEncargadoAgricola) {
        this.nombreEncargadoAgricola = nombreEncargadoAgricola;
    }
}
