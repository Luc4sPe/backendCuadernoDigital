package com.tesis.backendCuadernoDigital.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cuadro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCuadro;
    @NotNull
    private int numeroCuadro;
    @NotNull
    private float superficieHectarea;

    @ManyToOne(optional = false,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("numerosDeCuadros")
    @JoinColumn(name = "TipoPlantacion")
    private Plantacion tipoPlantacion;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "idCuadro",fetch = FetchType.LAZY)
    private List<LaborSuelo> laboresDeSuelo = new ArrayList<>();

    public Cuadro() {
    }

    public Cuadro(@NotNull int numeroCuadro, @NotNull float superficieHectarea) {
        this.numeroCuadro = numeroCuadro;
        this.superficieHectarea = superficieHectarea;

    }

    public Long getIdCuadro() {
        return idCuadro;
    }

    public void setIdCuadro(Long idCuadro) {
        this.idCuadro = idCuadro;
    }

    public int getNumeroCuadro() {
        return numeroCuadro;
    }

    public void setNumeroCuadro(int numeroCuadro) {
        this.numeroCuadro = numeroCuadro;
    }

    public float getSuperficieHectarea() {
        return superficieHectarea;
    }

    public void setSuperficieHectarea(float superficieHectarea) {
        this.superficieHectarea = superficieHectarea;
    }

    public List<LaborSuelo> getLaboresDeSuelo() {
        return laboresDeSuelo;
    }

    public void setLaboresDeSuelo(List<LaborSuelo> laboresDeSuelo) {
        this.laboresDeSuelo = laboresDeSuelo;
    }
}
