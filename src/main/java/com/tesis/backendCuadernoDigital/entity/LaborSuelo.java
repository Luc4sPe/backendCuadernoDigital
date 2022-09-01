package com.tesis.backendCuadernoDigital.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class LaborSuelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String cultivoAnterior;
    @NotNull
    @CreationTimestamp
    private Date fechaLabor;
    @UpdateTimestamp
    private Date fechaModificacionLabor;
    @NotNull
    private String herramientasUtilizadas;
    @NotNull
    @ManyToOne(optional = false,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("laboresDeSuelo")
    @JoinColumn(name = "IdCuadro")
    private Cuadro idCuadro;
    @NotNull
    private String labor;
    @NotNull
    private String observacion;
    @NotNull
    private String justificacion;

    public LaborSuelo() {
    }

    public LaborSuelo(@NotNull String cultivoAnterior, @NotNull String herramientasUtilizadas, @NotNull Cuadro idCuadro, @NotNull String labor, @NotNull String observacion, @NotNull String justificacion) {
        this.cultivoAnterior = cultivoAnterior;
        this.fechaLabor = null;
        this.fechaModificacionLabor = null;
        this.herramientasUtilizadas = herramientasUtilizadas;
        this.idCuadro = idCuadro;
        this.labor = labor;
        this.observacion = observacion;
        this.justificacion = justificacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCultivoAnterior() {
        return cultivoAnterior;
    }

    public void setCultivoAnterior(String cultivoAnterior) {
        this.cultivoAnterior = cultivoAnterior;
    }

    public Date getFechaLabor() {
        return fechaLabor;
    }

    public void setFechaLabor(Date fechaLabor) {
        this.fechaLabor = fechaLabor;
    }

    public Date getFechaModificacionLabor() {
        return fechaModificacionLabor;
    }

    public void setFechaModificacionLabor(Date fechaModificacionLabor) {
        this.fechaModificacionLabor = fechaModificacionLabor;
    }

    public String getHerramientasUtilizadas() {
        return herramientasUtilizadas;
    }

    public void setHerramientasUtilizadas(String herramientasUtilizadas) {
        this.herramientasUtilizadas = herramientasUtilizadas;
    }

    public Cuadro getIdCuadro() {
        return idCuadro;
    }

    public void setIdCuadro(Cuadro idCuadro) {
        this.idCuadro = idCuadro;
    }

    public String getLabor() {
        return labor;
    }

    public void setLabor(String labor) {
        this.labor = labor;
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
}


