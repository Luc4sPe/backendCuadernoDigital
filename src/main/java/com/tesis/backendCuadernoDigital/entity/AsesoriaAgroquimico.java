package com.tesis.backendCuadernoDigital.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    private Date fechaAsesoria;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date fechaModificacion;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date fechaAplicacion;


    @ManyToOne(optional = false,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("cuadros")
    @JoinColumn(name = "idFinca")
    private Finca finca;


    @ManyToOne(optional = false,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Usuario productor;

    @NotNull
    private boolean asesoriaAplicada;

    public AsesoriaAgroquimico() {
    }

    public AsesoriaAgroquimico(Agroquimico agroquimico, Cuadro cuadro, @NotNull float dosisPorHectaria, @NotNull float dosisPorHl, @NotNull float volumenPorHectaria, @NotNull String objetivo, @NotNull String plaga, Finca finca, Usuario productor) {
        this.agroquimico = agroquimico;
        this.cuadro = cuadro;
        this.dosisPorHectaria = dosisPorHectaria;
        this.dosisPorHl = dosisPorHl;
        this.volumenPorHectaria = volumenPorHectaria;
        this.objetivo = objetivo;
        this.plaga = plaga;
        this.finca = finca;
        this.productor = productor;
        this.fechaAsesoria=null;
        this.asesoriaAplicada=false;
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

    public Date getFechaAsesoria() {
        return fechaAsesoria;
    }

    public void setFechaAsesoria(Date fechaAsesoria) {
        this.fechaAsesoria = fechaAsesoria;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Date getFechaAplicacion() {
        return fechaAplicacion;
    }

    public void setFechaAplicacion(Date fechaAplicacion) {
        this.fechaAplicacion = fechaAplicacion;
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
        this.fechaAplicacion = new Date();

    }

    @PreUpdate
    public void fechaModificacion() {
        this.fechaModificacion = new Date();
    }





}
