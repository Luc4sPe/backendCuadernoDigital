package com.tesis.backendCuadernoDigital.entity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    private Date fechaCreacionPlantacion;

    @UpdateTimestamp
    private Date fechaModificacionPlantacion;

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
    private Cultivo nombreTipoCultivo;
    @NotNull
    private int cantidadPlantines;

    public Plantacion() {
    }

    public Plantacion(@NotNull float entreIleras, @NotNull float entrePlantas, @NotNull String observacion, @NotNull String justificacion, @NotNull String sistemaRiego, @NotNull String sistemaTrasplante, @NotNull Cultivo nombreTipoCultivo, @NotNull int cantidadPlantines) {
        this.entreIleras = entreIleras;
        this.entrePlantas = entrePlantas;
        this.observacion = observacion;
        this.justificacion = justificacion;
        this.sistemaRiego = sistemaRiego;
        this.sistemaTrasplante = sistemaTrasplante;
        this.nombreTipoCultivo = nombreTipoCultivo;
        this.cantidadPlantines = cantidadPlantines;
        this.fechaCreacionPlantacion = null;
        this.fechaModificacionPlantacion = null;
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

    public Date getFechaCreacionPlantacion() {
        return fechaCreacionPlantacion;
    }

    public void setFechaCreacionPlantacion(Date fechaCreacionPlantacion) {
        this.fechaCreacionPlantacion = fechaCreacionPlantacion;
    }

    public Date getFechaModificacionPlantacion() {
        return fechaModificacionPlantacion;
    }

    public void setFechaModificacionPlantacion(Date fechaModificacionPlantacion) {
        this.fechaModificacionPlantacion = fechaModificacionPlantacion;
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

    public Cultivo getNombreTipoCultivo() {
        return nombreTipoCultivo;
    }

    public void setNombreTipoCultivo(Cultivo nombreTipoCultivo) {
        this.nombreTipoCultivo = nombreTipoCultivo;
    }

    public int getCantidadPlantines() {
        return cantidadPlantines;
    }

    public void setCantidadPlantines(int cantidadPlantines) {
        this.cantidadPlantines = cantidadPlantines;
    }
}
