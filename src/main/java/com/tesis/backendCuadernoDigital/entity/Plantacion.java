package com.tesis.backendCuadernoDigital.entity;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
public class Plantacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlantacion;
    @NotNull
    private float entreIleras;
    @NotNull
    private float entrePlantas;
    @CreationTimestamp
    private Date fechaTrasplante;
    @NotNull
    private Date fechaSiembra;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "tipoPlantacion",fetch = FetchType.LAZY)
    private List<Cuadro> numerosDeCuadros = new ArrayList<>();
    @NotNull
    private String observacion;
    @NotNull
    private String justificacion;
    @NotNull
    private String sistemaRiego;
    @NotNull
    private String sistemaTrasplante;
    @NotNull
    @ManyToOne(optional = false,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "TipoCultivo")
    private Cultivo tipoCultivo;
    @NotNull
    private int cantidadPlantines;

    public Plantacion() {
    }

    public Plantacion(@NotNull float entreIleras, @NotNull float entrePlantas, Date fechaTrasplante, @NotNull Date fechaSiembra, @NotNull String observacion, @NotNull String justificacion, @NotNull String sistemaRiego, @NotNull String sistemaTrasplante, @NotNull Cultivo tipoCultivo, @NotNull int cantidadPlantines) {
        this.entreIleras = entreIleras;
        this.entrePlantas = entrePlantas;
        this.fechaTrasplante = fechaTrasplante;
        this.fechaSiembra = fechaSiembra;
        this.observacion = observacion;
        this.justificacion = justificacion;
        this.sistemaRiego = sistemaRiego;
        this.sistemaTrasplante = sistemaTrasplante;
        this.tipoCultivo = tipoCultivo;
        this.cantidadPlantines = cantidadPlantines;
    }

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

    public Date getFechaTrasplante() {
        return fechaTrasplante;
    }

    public void setFechaTrasplante(Date fechaTrasplante) {
        this.fechaTrasplante = fechaTrasplante;
    }

    public Date getFechaSiembra() {
        return fechaSiembra;
    }

    public void setFechaSiembra(Date fechaSiembra) {
        this.fechaSiembra = fechaSiembra;
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

    public Cultivo getTipoCultivo() {
        return tipoCultivo;
    }

    public void setTipoCultivo(Cultivo tipoCultivo) {
        this.tipoCultivo = tipoCultivo;
    }

    public int getCantidadPlantines() {
        return cantidadPlantines;
    }

    public void setCantidadPlantines(int cantidadPlantines) {
        this.cantidadPlantines = cantidadPlantines;
    }
}
