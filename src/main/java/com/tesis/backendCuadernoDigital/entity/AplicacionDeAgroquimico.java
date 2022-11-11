package com.tesis.backendCuadernoDigital.entity;

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
    private long id;
    @NotNull
    @ManyToOne(optional = false)
    @JsonIgnoreProperties("aplicacionAgroquimico")
    @JoinColumn(name = "Agroquimico")
    private Agroquimico agroquimico;
    @NotNull
    private String aplicacion;
    @NotNull
    private int numeroCuadro;
    @NotNull
    private String observaciones;

    @NotNull
    @ManyToOne(optional = false)
    @JsonIgnoreProperties("aplicacionAgroquimico")
    @JoinColumn(name = "Plagas")
    private Plaga plaga;
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "nombreUsuarioProductor")
    private Usuario nombreUsuario;

    @CreationTimestamp
    private Date fechaDeAplicacion;

    @UpdateTimestamp
    private Date fechaModificacion;



    public AplicacionDeAgroquimico() {
    }

    public AplicacionDeAgroquimico(@NotNull Agroquimico agroquimico, @NotNull String aplicacion, @NotNull int numeroCuadro, @NotNull String observaciones, @NotNull Plaga plaga, @NotNull Usuario nombreUsuario) {
        this.agroquimico = agroquimico;
        this.aplicacion = aplicacion;
        this.numeroCuadro = numeroCuadro;
        this.observaciones = observaciones;
        this.plaga = plaga;
        this.nombreUsuario = nombreUsuario;
        this.fechaDeAplicacion=null;
        this.fechaModificacion=null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Agroquimico getAgroquimico() {
        return agroquimico;
    }

    public void setAgroquimico(Agroquimico agroquimico) {
        this.agroquimico = agroquimico;
    }

    public String getAplicacion() {
        return aplicacion;
    }

    public void setAplicacion(String aplicacion) {
        this.aplicacion = aplicacion;
    }

    public int getNumeroCuadro() {
        return numeroCuadro;
    }

    public void setNumeroCuadro(int numeroCuadro) {
        this.numeroCuadro = numeroCuadro;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Plaga getPlaga() {
        return plaga;
    }

    public void setPlaga(Plaga plaga) {
        this.plaga = plaga;
    }

    public Usuario getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(Usuario nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
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
}
