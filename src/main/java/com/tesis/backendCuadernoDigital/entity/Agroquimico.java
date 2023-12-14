package com.tesis.backendCuadernoDigital.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Agroquimico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(unique = true)
    private String nombreComercial;
    @NotNull
    private String formulaYconcentracion;
    @NotNull
    private String principioActivo;
    @NotNull
    private String tipo;
    @NotNull
    private int tiempoDeCarencia;
    @NotNull
    private String dosisPorHectaria;
    @NotNull
    private String dosisPorHl;
    @NotNull
    private String volumenPorHectaria;
   @NotNull
    @Column(unique = true)
    private String numLote;

    @CreationTimestamp
    private Date fechaCreacionAgro;
    @UpdateTimestamp
    private Date fechaModificacionAgro;

    public Agroquimico() {
    }

    public Agroquimico(@NotNull String nombreComercial, @NotNull String formulaYconcentracion, @NotNull String principioActivo, @NotNull String tipo, @NotNull int tiempoDeCarencia, @NotNull String dosisPorHectaria,
                       @NotNull String dosisPorHl, @NotNull String volumenPorHectaria, @NotNull String numLote) {
        this.nombreComercial = nombreComercial;
        this.formulaYconcentracion = formulaYconcentracion;
        this.principioActivo = principioActivo;
        this.tipo = tipo;
        this.tiempoDeCarencia = tiempoDeCarencia;
        this.dosisPorHectaria = dosisPorHectaria;
        this.dosisPorHl = dosisPorHl;
        this.volumenPorHectaria = volumenPorHectaria;
        this.numLote=numLote;
        this.fechaCreacionAgro=null;
        this.fechaModificacionAgro=null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getFormulaYconcentracion() {
        return formulaYconcentracion;
    }

    public void setFormulaYconcentracion(String formulaYconcentracion) {
        this.formulaYconcentracion = formulaYconcentracion;
    }

    public String getPrincipioActivo() {
        return principioActivo;
    }

    public void setPrincipioActivo(String principioActivo) {
        this.principioActivo = principioActivo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getTiempoDeCarencia() {
        return tiempoDeCarencia;
    }

    public void setTiempoDeCarencia(int tiempoDeCarencia) {
        this.tiempoDeCarencia = tiempoDeCarencia;
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

    public String getNumLote() {
        return numLote;
    }

    public void setNumLote(String numLote) {
        this.numLote = numLote;
    }

    public Date getFechaCreacionAgro() {
        return fechaCreacionAgro;
    }

    public void setFechaCreacionAgro(Date fechaCreacionAgro) {
        this.fechaCreacionAgro = fechaCreacionAgro;
    }

    public Date getFechaModificacionAgro() {
        return fechaModificacionAgro;
    }

    public void setFechaModificacionAgro(Date fechaModificacionAgro) {
        this.fechaModificacionAgro = fechaModificacionAgro;
    }



}
