package com.tesis.backendCuadernoDigital.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AsesoriaAgroquimicoDto {

    @NotNull(message = "El id no puede ser un valor nulo")
    @Min(value = 1, message = "El minimo de entre ileras es 1")
    private Long id;

    @NotNull(message = "El idAgroquimico no debe estar vacio")
    @Min(value = 1, message = "El minimo valor del id es 1")
    private Long idAgroquimico;

    @NotNull(message = "El idCuadro no debe estar vacio")
    @Min(value = 1, message = "El minimo valor del id es 1")
    private Long idCuadro;

    @NotNull(message = "La dosis no debe estar vacia")
    @Min(value = 1, message = "El minimo valor del id es 1")
    private String dosisPorHectaria;

    @NotNull(message = "La dosis no debe estar vacia")
    @Min(value = 1, message = "El minimo valor del id es 1")
    private String dosisPorHl;

    @NotNull(message = "La dosis no debe estar vacia")
    @Min(value = 1, message = "El minimo valor del id es 1")
    private String volumenPorHectaria;

    @NotBlank(message = "El objetivo no debe estar vacio")
    private String objetivo;

    @NotBlank(message = "El objetivo no debe estar vacio")
    private String plaga;

    @NotNull(message = "la fecha estimada de aplicación es obligatoria")
    private LocalDate fechaEstimadaAplicacion;

    @NotNull(message = "La finca es obligatoria ")
    @Min(value = 1, message = "El minimo valor del id es 1")
    private Long idFinca;


    @NotBlank(message = "El campo Sistema de Trasplante no puede estar vacio")
    private String nombreProductor;

    @NotBlank(message = "No puede ser un valor nulo")
    private String nombreEncargado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdAgroquimico() {
        return idAgroquimico;
    }

    public void setIdAgroquimico(Long idAgroquimico) {
        this.idAgroquimico = idAgroquimico;
    }

    public Long getIdCuadro() {
        return idCuadro;
    }

    public void setIdCuadro(Long idCuadro) {
        this.idCuadro = idCuadro;
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

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getPlaga() {
        return plaga;
    }

    public void setPlaga(String plaga) {
        this.plaga = plaga;
    }

    public LocalDate getFechaEstimadaAplicacion() {
        return fechaEstimadaAplicacion;
    }

    public void setFechaEstimadaAplicacion(LocalDate fechaEstimadaAplicacion) {
        this.fechaEstimadaAplicacion = fechaEstimadaAplicacion;
    }

    public Long getIdFinca() {
        return idFinca;
    }

    public void setIdFinca(Long idFinca) {
        this.idFinca = idFinca;
    }

    public String getNombreProductor() {
        return nombreProductor;
    }

    public void setNombreProductor(String nombreProductor) {
        this.nombreProductor = nombreProductor;
    }

    public String getNombreEncargado() {
        return nombreEncargado;
    }

    public void setNombreEncargado(String nombreEncargado) {
        this.nombreEncargado = nombreEncargado;
    }
}
