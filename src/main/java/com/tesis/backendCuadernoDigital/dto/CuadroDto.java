package com.tesis.backendCuadernoDigital.dto;

import com.tesis.backendCuadernoDigital.entity.LaborSuelo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class CuadroDto {

    @NotNull(message = "No puede ser un valor nulo")
    private Long idCuadro;

    @NotBlank(message = "No puede ser un valor nulo")
    private String numeroCuadro;

   // @NotNull(message = "No puede ser un valor nulo")
    //@Min(value = 1, message = "El minimo de id 1")
    private float superficieHectarea;

    private List<LaborSuelo> laboresDeSuelo = new ArrayList<>();

    @NotBlank(message = "El Cultivo Anterior no puede estar vacio")
    private String cultivoAnterior;


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

    public List<LaborSuelo> getLaboresDeSuelo() {
        return laboresDeSuelo;
    }

    public void setLaboresDeSuelo(List<LaborSuelo> laboresDeSuelo) {
        this.laboresDeSuelo = laboresDeSuelo;
    }

    public String getCultivoAnterior() {
        return cultivoAnterior;
    }

    public void setCultivoAnterior(String cultivoAnterior) {
        this.cultivoAnterior = cultivoAnterior;
    }
}
