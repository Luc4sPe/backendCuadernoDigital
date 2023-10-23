package com.tesis.backendCuadernoDigital.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
@Entity
public class AsesoriaAgroquimico {

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
    private float dosisPorHectaria;
    @NotNull
    private float dosisPorHl;

    @NotNull
    private float volumenPorHectaria;

    @NotNull
    private String objetivo;

    @NotNull
    private String plaga;

    @CreationTimestamp
    private Date fechaAsesoriaAgroquimico;

    private Date fechaModificacionAsesoriaAgroquimico;

    private Date fechaAplicacionAsesoria;

    private LocalDate fechaEstimadaAplicacion;




    @ManyToOne(optional = false,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("cuadros")
    @JoinColumn(name = "idFinca")
    private Finca finca;


    @ManyToOne(optional = false,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Usuario productor;

    @NotNull
    private boolean asesoriaAplicada;

    @NotNull
    private String nombreEncargado;


    public AsesoriaAgroquimico() {
    }

    public AsesoriaAgroquimico(Agroquimico agroquimico, Cuadro cuadro, @NotNull float dosisPorHectaria, @NotNull float dosisPorHl, @NotNull float volumenPorHectaria, @NotNull String objetivo, @NotNull String plaga,LocalDate fechaEstimadaAplicacion,Finca finca, Usuario productor, String nombreEncargado) {
        this.agroquimico = agroquimico;
        this.cuadro = cuadro;
        this.dosisPorHectaria = dosisPorHectaria;
        this.dosisPorHl = dosisPorHl;
        this.volumenPorHectaria = volumenPorHectaria;
        this.objetivo = objetivo;
        this.plaga = plaga;
        this.finca = finca;
        this.productor = productor;
        this.fechaAsesoriaAgroquimico=null;
        this.fechaEstimadaAplicacion=fechaEstimadaAplicacion;
        this.nombreEncargado = nombreEncargado;
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

    public Date getFechaAsesoriaAgroquimico() {
        return fechaAsesoriaAgroquimico;
    }

    public void setFechaAsesoriaAgroquimico(Date fechaAsesoriaAgroquimico) {
        this.fechaAsesoriaAgroquimico = fechaAsesoriaAgroquimico;
    }

    public Date getFechaModificacionAsesoriaAgroquimico() {
        return fechaModificacionAsesoriaAgroquimico;
    }

    public void setFechaModificacionAsesoriaAgroquimico(Date fechaModificacionAsesoriaAgroquimico) {
        this.fechaModificacionAsesoriaAgroquimico = fechaModificacionAsesoriaAgroquimico;
    }

    public Date getFechaAplicacionAsesoria() {
        return fechaAplicacionAsesoria;
    }



    public void setFechaAplicacionAsesoria(Date fechaAplicacionAsesoria) {
        this.fechaAplicacionAsesoria = fechaAplicacionAsesoria;
    }

    public String getFechaEstimadaAplicacionParsed() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return fechaEstimadaAplicacion.atTime(1,1).toString();

    }

    @JsonIgnore
    public LocalDate getFechaEstimadaAplicacion() {
        return fechaEstimadaAplicacion;
    }

    public void setFechaEstimadaAplicacion(LocalDate fechaEstimadaAplicacion) {
        this.fechaEstimadaAplicacion = fechaEstimadaAplicacion;
    }

    public Finca getFinca() {
        return finca;
    }

    public void setFinca(Finca finca) {
        this.finca = finca;
    }

    public Usuario getProductor() {
        return productor;
    }

    public void setProductor(Usuario productor) {
        this.productor = productor;
    }

    public boolean isAsesoriaAplicada() {
        return asesoriaAplicada;
    }

    public void setAsesoriaAplicada(boolean asesoriaAplicada) {
        this.asesoriaAplicada = asesoriaAplicada;
    }

    public void modificarEstado(){
        this.asesoriaAplicada=!isAsesoriaAplicada();
        this.fechaAplicacionAsesoria = new Date();

    }

    @PreUpdate
    public void fechaModificacionAsesoriaAgroquimico() {
        this.fechaModificacionAsesoriaAgroquimico = new Date();
    }

    public String getNombreEncargado() {
        return nombreEncargado;
    }

    public void setNombreEncargado(String nombreEncargado) {
        this.nombreEncargado = nombreEncargado;
    }
}
