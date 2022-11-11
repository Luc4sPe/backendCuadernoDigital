package com.tesis.backendCuadernoDigital.dto;

import com.tesis.backendCuadernoDigital.entity.Cuadro;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class ModificacionPlantacionDto {

    @NotNull(message = "No puede ser un valor nulo")
    private Long idPlantacion;
    @NotNull(message = "No puede ser un valor nulo")
    @Min(value = 1, message = "El minimo de entre ileras es 1")
    private float entreIleras;
    @NotNull(message = "No puede ser un valor nulo")
    @Min(value = 1, message = "El minimo de entre plantas es 1")
    private float entrePlantas;

    @NotEmpty(message = "La lista no puede estar ")
    private List<Cuadro> numerosDeCuadros = new ArrayList<>();

    @NotBlank(message = "El campo observacion no puede estar vacio")
    private String observacion;
    @NotBlank(message = "El campo Justificacion no puede estar vacio")
    private String justificacion;
    @NotBlank(message = "El campo Sistema de Riego no puede estar vacio")
    private String sistemaRiego;
    @NotBlank(message = "El campo Sistema de Trasplante no puede estar vacio")
    private String sistemaTrasplante;
    @NotNull(message = "El campo Tipo de Cultivo no puede ser negativo")
    private Long tipoCultivo;
    @NotNull(message = "No puede ser un valor nulo")
    @Min(value = 1, message = "El minimo de plantines es 1")
    private int cantidadPlantines;

    @NotNull(message = "La finca es obligatoria ")
    private Long idFinca;

    public Long getIdPlantacion() {
        return idPlantacion;
    }

    public void setIdPlantacion(Long idPlantacion) {
        this.idPlantacion = idPlantacion;
    }

    public float getEntreIleras() {
        return entreIleras;
    }

    public void setEntreIleras(float entreIleras) {
        this.entreIleras = entreIleras;
    }

    public float getEntrePlantas() {
        return entrePlantas;
    }

    public void setEntrePlantas(float entrePlantas) {
        this.entrePlantas = entrePlantas;
    }

    public List<Cuadro> getNumerosDeCuadros() {
        return numerosDeCuadros;
    }

    public void setNumerosDeCuadros(List<Cuadro> numerosDeCuadros) {
        this.numerosDeCuadros = numerosDeCuadros;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public String getSistemaRiego() {
        return sistemaRiego;
    }

    public void setSistemaRiego(String sistemaRiego) {
        this.sistemaRiego = sistemaRiego;
    }

    public String getSistemaTrasplante() {
        return sistemaTrasplante;
    }

    public void setSistemaTrasplante(String sistemaTrasplante) {
        this.sistemaTrasplante = sistemaTrasplante;
    }

    public Long getTipoCultivo() {
        return tipoCultivo;
    }

    public void setTipoCultivo(Long tipoCultivo) {
        this.tipoCultivo = tipoCultivo;
    }

    public int getCantidadPlantines() {
        return cantidadPlantines;
    }

    public void setCantidadPlantines(int cantidadPlantines) {
        this.cantidadPlantines = cantidadPlantines;
    }

    public Long getIdFinca() {
        return idFinca;
    }

    public void setIdFinca(Long idFinca) {
        this.idFinca = idFinca;
    }
}
