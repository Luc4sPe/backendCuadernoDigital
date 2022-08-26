package com.tesis.backendCuadernoDigital.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Cultivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCultivo;
    @NotNull
    private String nombre;
    @NotNull
    private String remito;
    @NotNull
    private int timpoCarencia;
    @NotNull
    private String variedadCultivo;
    @NotNull
    private String viveroProvedor;

    @CreationTimestamp
    private Date fechaCreacion;

    @UpdateTimestamp
    private Date fechaModificacion;

    public Cultivo() {
    }

    public Cultivo(@NotNull String nombre, @NotNull String remito, @NotNull int timpoCarencia, @NotNull String variedadCultivo, @NotNull String viveroProvedor) {
        this.nombre = nombre;
        this.remito = remito;
        this.timpoCarencia = timpoCarencia;
        this.variedadCultivo = variedadCultivo;
        this.viveroProvedor = viveroProvedor;
        this.fechaCreacion = null;
        this.fechaModificacion = null;
    }

    public Long getIdCultivo() {
        return idCultivo;
    }

    public void setIdCultivo(Long idCultivo) {
        this.idCultivo = idCultivo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRemito() {
        return remito;
    }

    public void setRemito(String remito) {
        this.remito = remito;
    }

    public int getTimpoCarencia() {
        return timpoCarencia;
    }

    public void setTimpoCarencia(int timpoCarencia) {
        this.timpoCarencia = timpoCarencia;
    }

    public String getVariedadCultivo() {
        return variedadCultivo;
    }

    public void setVariedadCultivo(String variedadCultivo) {
        this.variedadCultivo = variedadCultivo;
    }

    public String getViveroProvedor() {
        return viveroProvedor;
    }

    public void setViveroProvedor(String viveroProvedor) {
        this.viveroProvedor = viveroProvedor;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
}


