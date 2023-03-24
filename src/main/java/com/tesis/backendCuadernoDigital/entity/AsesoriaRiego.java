package com.tesis.backendCuadernoDigital.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.Date;

@Entity
public class AsesoriaRiego {

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


    @ManyToOne(optional = false,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("cuadros")
    @JoinColumn(name = "fincaId")
    private Finca finca;

    @ManyToOne(optional = false,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Usuario productor;

    @CreationTimestamp
    private Date fechaRiego;

    @UpdateTimestamp
    private Date fechaModificacionRiego;

    public AsesoriaRiego() {
    }

    public AsesoriaRiego(@NotNull LocalTime duracionEnHoras, @NotNull float milimetrosAplicados, Cuadro idCuadros, Finca finca, Usuario productor) {
        this.duracionEnHoras = duracionEnHoras;
        this.milimetrosAplicados = milimetrosAplicados;
        this.idCuadros = idCuadros;
        this.finca = finca;
        this.productor = productor;
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

    public Cuadro getIdCuadros() {
        return idCuadros;
    }

    public void setIdCuadros(Cuadro idCuadros) {
        this.idCuadros = idCuadros;
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


