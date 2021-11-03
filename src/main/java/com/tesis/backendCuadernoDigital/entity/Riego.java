package com.tesis.backendCuadernoDigital.entity;

import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Date;
import javax.validation.constraints.NotNull;

@Entity
public class Riego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private LocalTime duracionEnHoras;
    @CreationTimestamp
    private Date fechaAplicacion;
    @NotNull
    private float milimetrosAplicados;
    @NotNull
    private int numeroDeCuadro;
    @NotNull
    private String observacionAsesor;
    @NotNull
    private String observacionProductor;
    @NotNull
    private int semanaAplicada;
    @NotNull
    private int semanaTransplante;

    @NotNull
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn (name = "nombreUsuarioProductor")
    private Usuario nombreUsuario;

    public Riego() {
    }

    public Riego(@NotNull LocalTime duracionEnHoras, @NotNull float milimetrosAplicados, @NotNull int numeroDeCuadro, @NotNull String observacionAsesor ,@NotNull String observacionProductor, @NotNull int semanaAplicada, @NotNull int semanaTransplante
    ,@NotNull Usuario nombreUsuario) {
        this.duracionEnHoras = duracionEnHoras;
        this.fechaAplicacion = null;
        this.milimetrosAplicados = milimetrosAplicados;
        this.numeroDeCuadro = numeroDeCuadro;
        this.observacionAsesor=observacionAsesor;
        this.observacionProductor = observacionProductor;
        this.semanaAplicada = semanaAplicada;
        this.semanaTransplante = semanaTransplante;
        this.nombreUsuario=nombreUsuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalTime getDuracionEnHoras() {
        return duracionEnHoras;
    }

    public void setDuracionEnHoras(LocalTime duracionEnHoras) {
        this.duracionEnHoras = duracionEnHoras;
    }

    public Date getFechaAplicacion() {
        return fechaAplicacion;
    }

    public void setFechaAplicacion(Date fechaAplicacion) {
        this.fechaAplicacion = fechaAplicacion;
    }

    public float getMilimetrosAplicados() {
        return milimetrosAplicados;
    }

    public void setMilimetrosAplicados(float milimetrosAplicados) {
        this.milimetrosAplicados = milimetrosAplicados;
    }

    public int getNumeroDeCuadro() {
        return numeroDeCuadro;
    }

    public void setNumeroDeCuadro(int numeroDeCuadro) {
        this.numeroDeCuadro = numeroDeCuadro;
    }

    public String getObservacionAsesor() {
        return observacionAsesor;
    }

    public void setObservacionAsesor(String observacionAsesor) {
        this.observacionAsesor = observacionAsesor;
    }

    public String getObservacionProductor() {
        return observacionProductor;
    }

    public void setObservacionProductor(String observacionProductor) {
        this.observacionProductor = observacionProductor;
    }

    public int getSemanaAplicada() {
        return semanaAplicada;
    }

    public void setSemanaAplicada(int semanaAplicada) {
        this.semanaAplicada = semanaAplicada;
    }

    public int getSemanaTransplante() {
        return semanaTransplante;
    }

    public void setSemanaTransplante(int semanaTransplante) {
        this.semanaTransplante = semanaTransplante;
    }

    public Usuario getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(Usuario nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
