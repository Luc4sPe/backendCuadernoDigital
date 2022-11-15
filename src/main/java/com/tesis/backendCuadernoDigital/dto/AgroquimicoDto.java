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
    private Long tiempoDeCarencia;
    @NotNull(message = "La dosis por hectaria no debe estar vacio")
    @Min(value = 1, message = "El minimo valor de dosis por hectaria es 1")
    private float dosisPorHectaria;
    @NotNull(message = "La dosis por Hl no debe estar vacio")
    @Min(value = 1, message = "El minimo valor de dosis por Hl es 1")
    private float dosisPorHl;
    @NotNull(message = "El volumen por hectaria no debe estar vacio")
    @Min(value = 1, message = "El minimo valor de dosis por hectaria es 1")
    private float volumenPorHectaria;
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

    public Long getTiempoDeCarencia() {
        return tiempoDeCarencia;
    }

    public void setTiempoDeCarencia(Long tiempoDeCarencia) {
        this.tiempoDeCarencia = tiempoDeCarencia;
    }

    public float getDosisPorHectaria() {
        return dosisPorHectaria;
    }

    public void setDosisPorHectaria(float dosisPorHectaria) {
        this.dosisPorHectaria = dosisPorHectaria;
    }

    public float getDosisPorHl() {
        return dosisPorHl;
    }

    public void setDosisPorHl(float dosisPorHl) {
        this.dosisPorHl = dosisPorHl;
    }

    public float getVolumenPorHectaria() {
        return volumenPorHectaria;
    }

    public void setVolumenPorHectaria(float volumenPorHectaria) {
        this.volumenPorHectaria = volumenPorHectaria;
    }

    public String getNumLote() {
        return numLote;
    }

    public void setNumLote(String numLote) {
        this.numLote = numLote;
    }
}


