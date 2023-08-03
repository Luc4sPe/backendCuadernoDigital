package com.tesis.backendCuadernoDigital.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ModificarAsesoriaAgroquimicoDto {

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
    private float dosisPorHectaria;

    @NotNull(message = "La dosis no debe estar vacia")
    @Min(value = 1, message = "El minimo valor del id es 1")
    private float dosisPorHl;

    @NotNull(message = "La dosis no debe estar vacia")
    @Min(value = 1, message = "El minimo valor del id es 1")
    private float volumenPorHectaria;

    @NotBlank(message = "El objetivo no debe estar vacio")
    private String objetivo;

    @NotBlank(message = "El objetivo no debe estar vacio")
    private String plaga;

    @NotNull(message = "La finca es obligatoria ")
    @Min(value = 1, message = "El minimo valor del id es 1")
    private Long idFinca;


    @NotBlank(message = "El campo Sistema de Trasplante no puede estar vacio")
    private String nombreProductor;

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
}
