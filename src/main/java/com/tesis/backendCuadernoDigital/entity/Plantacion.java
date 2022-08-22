package com.tesis.backendCuadernoDigital.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

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
    @NotNull
    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "Id Cuadro")
    private Cuadro idCuadro;
    @NotNull
    private String observacion;
    @NotNull
    private String justificacion;
    @NotNull
    private String sistemaRiego;
    @NotNull
    private String sistemaTrasplante;
    @NotNull
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "Tipo Cultivo")
    private Cultivo tipoCultivo;
    @NotNull
    private int cantidadPlantines;

    public Plantacion() {
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

    public Cuadro getIdCuadro() {
        return idCuadro;
    }

    public void setIdCuadro(Cuadro idCuadro) {
        this.idCuadro = idCuadro;
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
