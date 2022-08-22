package com.tesis.backendCuadernoDigital.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Cultivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCultivo;
    @NotNull
    private String nombre;
    @NotNull
    private String remito;
    @NotNull
    private int timpoCarencia;
    @NotNull
    private String variedadCultivo;
    @NotNull
    private String viveroProvedor;


}
