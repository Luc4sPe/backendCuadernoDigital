package com.tesis.backendCuadernoDigital.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    private float milimetrosAplicados;

    @ManyToOne(optional = false,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("riegos")
    @JoinColumn(name = "cuadroId")
    private Cuadro idCuadros;

    @NotNull
    private String observacionProductor;

    // Solo se utiliza para modificar
    @NotNull
    private String justificacionProductor;

    //diferencia entre fechas de plantacion y riego
  //  @NotNull
    //private int semanaDesdeElTrasplante;

    @ManyToOne(optional = false,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("cuadros")
    @JoinColumn(name = "fincaId")
    private Finca finca;




    @CreationTimestamp
    private Date fechaRiego;
    @UpdateTimestamp
    private Date fechaModificacionRiego;

    public Riego() {
    }

    public Riego(@NotNull LocalTime duracionEnHoras, @NotNull float milimetrosAplicados, @NotNull Cuadro idCuadros, @NotNull String observacionProductor, @NotNull String justificacionProductor, @NotNull Finca finca) {
        this.duracionEnHoras = duracionEnHoras;
        this.milimetrosAplicados = milimetrosAplicados;
        this.idCuadros=idCuadros;
        this.observacionProductor = observacionProductor;
        this.justificacionProductor = justificacionProductor;
      //  this.semanaDesdeElTrasplante = semanaDesdeElTrasplante;
        this.finca=finca;

        this.fechaRiego = null;
        this.fechaModificacionRiego = null;

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


    public float getMilimetrosAplicados() {
        return milimetrosAplicados;
    }

    public void setMilimetrosAplicados(float milimetrosAplicados) {
        this.milimetrosAplicados = milimetrosAplicados;
    }

    public Cuadro getIdCuadro() {
        return idCuadros;
    }

    public void setIdCuadro(Cuadro idCuadro) {
        this.idCuadros = idCuadro;
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

    /*
    public int getSemanaDesdeElTrasplante() {
        return semanaDesdeElTrasplante;
    }

    public void setSemanaDesdeElTrasplante(int semanaDesdeElTrasplante) {
        this.semanaDesdeElTrasplante = semanaDesdeElTrasplante;
    }

     */

    public Finca getFinca() {
        return finca;
    }

    public void setFinca(Finca finca) {
        this.finca = finca;
    }



    public Date getFechaRiego() {
        return fechaRiego;
    }

    public void setFechaRiego(Date fechaRiego) {
        this.fechaRiego = fechaRiego;
    }

    public Date getFechaModificacionRiego() {
        return fechaModificacionRiego;
    }

    public void setFechaModificacionRiego(Date fechaModificacionRiego) {
        this.fechaModificacionRiego = fechaModificacionRiego;
    }
}

