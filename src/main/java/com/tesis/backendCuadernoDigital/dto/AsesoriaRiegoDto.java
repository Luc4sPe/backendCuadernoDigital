package com.tesis.backendCuadernoDigital.dto;

import com.tesis.backendCuadernoDigital.entity.Cuadro;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AsesoriaRiegoDto {

    @NotNull(message = "El id no puede ser un valor nulo")
    @Min(value = 1, message = "El minimo de entre ileras es 1")
    private Long id;
    @NotBlank
    private LocalTime duracionEnHoras;
    @NotBlank
    private float milimetrosAplicados;

    /*
    @NotEmpty(message = "La lista no puede estar vacia")
    private List<@NotNull Cuadro> numerosDeCuadros = new ArrayList<>();

     */
    @NotNull(message = "La finca es obligatoria ")
    private Long idFinca;

    @NotNull(message = "El id no puede ser un valor nulo")
    @Min(value = 1, message = "El minimo es 1")
    private Long idCuadro;

    @NotBlank(message = "El campo Sistema de Trasplante no puede estar vacio")
    private String nombreProductor;


    private LocalDate fechaEstimadaAplicacion;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getDuracionEnHoras() {
        return duracionEnHoras;
    }

    public void setDuracionEnHoras(LocalTime duracionEnHoras) {
        this.duracionEnHoras = duracionEnHoras;
    }

    public float getMilimetrosAplicados() {
        return milimetrosAplicados;
    }

    public void setMilimetrosAplicados(float milimetrosAplicados) {
        this.milimetrosAplicados = milimetrosAplicados;
    }

    public Long getIdFinca() {
        return idFinca;
    }

    public void setIdFinca(Long idFinca) {
        this.idFinca = idFinca;
    }

    public Long getIdCuadro() {
        return idCuadro;
    }

    public void setIdCuadro(Long idCuadro) {
        this.idCuadro = idCuadro;
    }

    public String getNombreProductor() {
        return nombreProductor;
    }

    public void setNombreProductor(String nombreProductor) {
        this.nombreProductor = nombreProductor;
    }

    public LocalDate getFechaEstimadaAplicacion() {
        return fechaEstimadaAplicacion;
    }

    public void setFechaEstimadaAplicacion(LocalDate fechaEstimadaAplicacion) {
        this.fechaEstimadaAplicacion = fechaEstimadaAplicacion;
    }
}
