package com.tesis.backendCuadernoDigital.dto;

import com.tesis.backendCuadernoDigital.security.entity.Usuario;

import javax.validation.constraints.NotBlank;
import java.time.LocalTime;
import java.util.Date;

public class RiegoDto {
    @NotBlank
    private int id;
    @NotBlank
    private LocalTime duracionEnHoras;
    @NotBlank
    private Date fechaAplicacion;
    @NotBlank
    private float milimetrosAplicados;
    @NotBlank
    private int numeroDeCuadro;
    @NotBlank
    private String observacionProductor;
    @NotBlank
    private int semanaAplicada;
    @NotBlank
    private int semanaTransplante;
    @NotBlank
    private String nombreUsuario;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalTime getDuracionEnHoras() {
        return duracionEnHoras;
    }

    public void setDuracionEnHoras(LocalTime duracionEnHoras) {
        this.duracionEnHoras = duracionEnHoras;
    }

    public Date getFechaAplicacion() {
        return fechaAplicacion;
    }

    public void setFechaAplicacion(Date fechaAplicacion) {
        this.fechaAplicacion = fechaAplicacion;
    }

    public float getMilimetrosAplicados() {
        return milimetrosAplicados;
    }

    public void setMilimetrosAplicados(float milimetrosAplicados) {
        this.milimetrosAplicados = milimetrosAplicados;
    }

    public int getNumeroDeCuadro() {
        return numeroDeCuadro;
    }

    public void setNumeroDeCuadro(int numeroDeCuadro) {
        this.numeroDeCuadro = numeroDeCuadro;
    }

    public String getObservacionProductor() {
        return observacionProductor;
    }

    public void setObservacionProductor(String observacionProductor) {
        this.observacionProductor = observacionProductor;
    }

    public int getSemanaAplicada() {
        return semanaAplicada;
    }

    public void setSemanaAplicada(int semanaAplicada) {
        this.semanaAplicada = semanaAplicada;
    }

    public int getSemanaTransplante() {
        return semanaTransplante;
    }

    public void setSemanaTransplante(int semanaTransplante) {
        this.semanaTransplante = semanaTransplante;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
