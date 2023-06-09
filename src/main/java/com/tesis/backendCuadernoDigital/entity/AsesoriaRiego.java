package com.tesis.backendCuadernoDigital.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class AsesoriaRiego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private LocalTime duracionEnHoras;

    @NotNull
    private float milimetrosAplicados;


    /*
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnoreProperties("finca")
    private List<Cuadro> numerosDeCuadros = new ArrayList<>();

     */



    @ManyToOne(optional = false,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("cuadros")
    @JoinColumn(name = "fincaId")
    private Finca finca;

    @ManyToOne(optional = false,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("riegos")
    @JoinColumn(name = "cuadroId")
    private Cuadro cuadro;

    @ManyToOne(optional = false,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Usuario productor;

    @NotNull
    private boolean asesoriaAplicada;

    @CreationTimestamp
    private Date fechaRiego;

    @UpdateTimestamp
    private Date fechaModificacionRiego;



    public AsesoriaRiego() {
    }

    public AsesoriaRiego(@NotNull LocalTime duracionEnHoras, @NotNull float milimetrosAplicados,Finca finca,Cuadro cuadro,Usuario productor) {
        this.duracionEnHoras = duracionEnHoras;
        this.milimetrosAplicados = milimetrosAplicados;
        this.finca = finca;
        this.cuadro=cuadro;
        this.productor = productor;
        this.asesoriaAplicada=false;
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

    public Finca getFinca() {
        return finca;
    }

    public void setFinca(Finca finca) {
        this.finca = finca;
    }

    public Cuadro getCuadro() {
        return cuadro;
    }

    public void setCuadro(Cuadro cuadro) {
        this.cuadro = cuadro;
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


