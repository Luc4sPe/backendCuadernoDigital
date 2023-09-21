package com.tesis.backendCuadernoDigital.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class AplicacionDeAgroquimico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "idAgroquimico")
    private Agroquimico agroquimico;

    @ManyToOne(optional = false,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("riegos")
    @JoinColumn(name = "idCuadro")
    private Cuadro cuadro;

    @NotNull
    private String dosisPorHectaria;
    @NotNull
    private String dosisPorHl;

    @NotNull
    private String volumenPorHectaria;

    @NotNull
    private String objetivo;


    @NotNull
    private String observaciones;


    private String justificacion;

    @NotNull
    private String plaga;

    @CreationTimestamp
    private Date fechaDeAplicacion;
    @UpdateTimestamp
    private Date fechaModificacion;

    @ManyToOne(optional = false,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("cuadros")
    @JoinColumn(name = "idFinca")
    private Finca finca;



    //@ManyToOne(optional = false,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //@JsonIgnore
    //private Plantacion plantacion;



    public AplicacionDeAgroquimico() {
    }

    public AplicacionDeAgroquimico(Agroquimico agroquimico, Cuadro cuadro, @NotNull String dosisPorHectaria, @NotNull String dosisPorHl, @NotNull String volumenPorHectaria, @NotNull String objetivo, @NotNull String observaciones,  String justificacion, @NotNull String plaga,@NotNull Finca finca) {
        this.agroquimico = agroquimico;
        this.cuadro = cuadro;
        this.dosisPorHectaria = dosisPorHectaria;
        this.dosisPorHl = dosisPorHl;
        this.volumenPorHectaria = volumenPorHectaria;
        this.objetivo = objetivo;
        this.observaciones = observaciones;
        this.justificacion = justificacion;
        this.plaga = plaga;
        this.fechaDeAplicacion=null;
        this.fechaModificacion=null;
        this.finca=finca;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Agroquimico getAgroquimico() {
        return agroquimico;
    }

    public void setAgroquimico(Agroquimico agroquimico) {
        this.agroquimico = agroquimico;
    }

    public Cuadro getCuadro() {
        return cuadro;
    }

    public void setCuadro(Cuadro cuadro) {
        this.cuadro = cuadro;
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

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public String getPlaga() {
        return plaga;
    }

    public void setPlaga(String plaga) {
        this.plaga = plaga;
    }

    public Date getFechaDeAplicacion() {
        return fechaDeAplicacion;
    }

    public void setFechaDeAplicacion(Date fechaDeAplicacion) {
        this.fechaDeAplicacion = fechaDeAplicacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Finca getFinca() {
        return finca;
    }

    public void setFinca(Finca finca) {
        this.finca = finca;
    }



    /* public Plantacion getPlantacion() {
        return plantacion;
    }

    public void setPlantacion(Plantacion plantacion) {
        this.plantacion = plantacion;
    }

    */


}


