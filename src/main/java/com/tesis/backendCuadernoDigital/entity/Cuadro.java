package com.tesis.backendCuadernoDigital.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Cuadro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCuadro;
    @NotNull
    private String numeroCuadro;
    @NotNull
    private float superficieHectarea;

   // @OneToMany(cascade = CascadeType.ALL,mappedBy = "idCuadro",fetch = FetchType.LAZY)
    //private List<LaborSuelo> laboresDeSuelo = new ArrayList<>();

    //@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    //private List<AplicacionDeAgroquimico> aplicacionDeAgroquimicos = new ArrayList<>();
    //falta lista de riego

    @CreationTimestamp
    private Date fechaCreacionCuadro;

    @UpdateTimestamp
    private Date fechaModificacionCuadro;

    public Cuadro() {
    }

    public Cuadro(@NotNull String numeroCuadro, @NotNull float superficieHectarea) {
        this.numeroCuadro = numeroCuadro;
        this.superficieHectarea = superficieHectarea;
    }

    public Long getIdCuadro() {
        return idCuadro;
    }

    public void setIdCuadro(Long idCuadro) {
        this.idCuadro = idCuadro;
    }

    public String getNumeroCuadro() {
        return numeroCuadro;
    }

    public void setNumeroCuadro(String numeroCuadro) {
        this.numeroCuadro = numeroCuadro;
    }

    public float getSuperficieHectarea() {
        return superficieHectarea;
    }

    public void setSuperficieHectarea(float superficieHectarea) {
        this.superficieHectarea = superficieHectarea;
    }

   // public List<LaborSuelo> getLaboresDeSuelo() {
     //   return laboresDeSuelo;
   // }

    //public void setLaboresDeSuelo(List<LaborSuelo> laboresDeSuelo) {
      //  this.laboresDeSuelo = laboresDeSuelo;
    //}

    public Date getFechaCreacionCuadro() {
        return fechaCreacionCuadro;
    }

    public void setFechaCreacionCuadro(Date fechaCreacionCuadro) {
        this.fechaCreacionCuadro = fechaCreacionCuadro;
    }

    public Date getFechaModificacionCuadro() {
        return fechaModificacionCuadro;
    }

    public void setFechaModificacionCuadro(Date fechaModificacionCuadro) {
        this.fechaModificacionCuadro = fechaModificacionCuadro;
    }

    public void enviarMiId(){
        this.idCuadro = idCuadro;
    }
}
