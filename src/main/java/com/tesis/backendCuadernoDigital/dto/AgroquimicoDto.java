package com.tesis.backendCuadernoDigital.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AgroquimicoDto {

    @NotBlank(message = "El nombre comercial no debe estar vacio")
    private String nombreComercial;
    @NotBlank(message = "La formulación no debe estar vacio")
    private String formulaYconcentracion;
    @NotBlank(message = "El principio no debe estar vacio")
    private String principioActivo;
    @NotBlank(message = "El tipo no debe estar vacio")
    private String tipo;
    @NotNull(message = "El tiempo de carencia no debe estar vacio")
    @Min(value = 1, message = "El minimo valor del tiempo de carencia es 1")
    private int tiempoDeCarencia;
    @NotBlank(message = "La docis por hectarea no debe estar vacio")
    private String dosisPorHectaria;
    @NotBlank(message = "La docis por HL no debe estar vacio")
    private String dosisPorHl;
    @NotBlank(message = "El volumen por hectarea no debe estar vacio")
    private String volumenPorHectaria;
    @NotBlank(message = "El numero de lote no debe estar vacio")
    private String numLote;


    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getFormulaYconcentracion() {
        return formulaYconcentracion;
    }

    public void setFormulaYconcentracion(String formulaYconcentracion) {
        this.formulaYconcentracion = formulaYconcentracion;
    }

    public String getPrincipioActivo() {
        return principioActivo;
    }

    public void setPrincipioActivo(String principioActivo) {
        this.principioActivo = principioActivo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getTiempoDeCarencia() {
        return tiempoDeCarencia;
    }

    public void setTiempoDeCarencia(int tiempoDeCarencia) {
        this.tiempoDeCarencia = tiempoDeCarencia;
    }

    public String getDosisPorHectaria() {
        return dosisPorHectaria;
    }

    public void setDosisPorHectaria(String dosisPorHectaria) {
        this.dosisPorHectaria = dosisPorHectaria;
    }

    public String getDosisPorHl() {
        return dosisPorHl;
    }

    public void setDosisPorHl(String dosisPorHl) {
        this.dosisPorHl = dosisPorHl;
    }

    public String getVolumenPorHectaria() {
        return volumenPorHectaria;
    }

    public void setVolumenPorHectaria(String volumenPorHectaria) {
        this.volumenPorHectaria = volumenPorHectaria;
    }

    public String getNumLote() {
        return numLote;
    }

    public void setNumLote(String numLote) {
        this.numLote = numLote;
    }
}


