package com.tesis.backendCuadernoDigital.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Cuadro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCuadro;
    @NotNull
    @Column(unique = true)
    private int numeroCuadro;
    @NotNull
    private float superficieHectarea;


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
}
