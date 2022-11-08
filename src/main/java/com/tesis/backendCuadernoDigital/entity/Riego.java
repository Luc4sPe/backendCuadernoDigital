package com.tesis.backendCuadernoDigital.entity;
/*
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
    private Long id;
    @NotNull
    private LocalTime duracionEnHoras;
    @NotNull
    private Date fechaAplicacion;
    @NotNull
    private float milimetrosAplicados;
    @NotNull
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn (name = "Numero de Cuadro")
    private Cuadro numeroDeCuadro;
    @NotNull
    private String observacionProductor;

    // Solo se utiliza para modificar
    @NotNull
    private String justificacionProductor;
    //diferencia entre fechas de plantacion y riego
    @NotNull
    private int semanaDesdeElTrasplante;
    @NotNull
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn (name = "Nombre Productor")
    private Usuario nombreUsuario;

    public Riego() {
    }

    public Riego(@NotNull LocalTime duracionEnHoras, @NotNull Date fechaAplicacion, @NotNull float milimetrosAplicados, @NotNull Cuadro numeroDeCuadro, @NotNull String observacionProductor, @NotNull String justificacionProductor, @NotNull int semanaDesdeElTrasplante, @NotNull Usuario nombreUsuario) {
        this.duracionEnHoras = duracionEnHoras;
        this.fechaAplicacion = fechaAplicacion;
        this.milimetrosAplicados = milimetrosAplicados;
        this.numeroDeCuadro = numeroDeCuadro;
        this.observacionProductor = observacionProductor;
        this.justificacionProductor = justificacionProductor;
        this.semanaDesdeElTrasplante = semanaDesdeElTrasplante;
        this.nombreUsuario = nombreUsuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Cuadro getNumeroDeCuadro() {
        return numeroDeCuadro;
    }

    public void setNumeroDeCuadro(Cuadro numeroDeCuadro) {
        this.numeroDeCuadro = numeroDeCuadro;
    }

    public String getObservacionProductor() {
        return observacionProductor;
    }

    public void setObservacionProductor(String observacionProductor) {
        this.observacionProductor = observacionProductor;
    }

    public String getJustificacionProductor() {
        return justificacionProductor;
    }

    public void setJustificacionProductor(String justificacionProductor) {
        this.justificacionProductor = justificacionProductor;
    }

    public int getSemanaDesdeElTrasplante() {
        return semanaDesdeElTrasplante;
    }

    public void setSemanaDesdeElTrasplante(int semanaDesdeElTrasplante) {
        this.semanaDesdeElTrasplante = semanaDesdeElTrasplante;
    }

    public Usuario getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(Usuario nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
*/