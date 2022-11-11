package com.tesis.backendCuadernoDigital.dto;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class LaborsueloDto {

    @NotNull(message = "No puede ser un valor nulo")
    private Long id;
    @NotBlank(message = "Las Herramentas no pueden estar vacias")
    private String herramientasUtilizadas;
    @NotNull(message = "El id no puede ser un valor nulo")
    @Min(value = 1, message = "El minimo de entre ileras es 1")
    private Long idCuadro;
    @NotBlank(message = "La labor no puede estar vacio")
    private String labor;
    @NotBlank(message = "La observacion no puede estar vacio")
    private String observacion;

    @NotNull(message = "La finca es obligatoria ")
    private Long idFinca;

    @NotBlank(message = "El Cultivo Anterior no puede estar vacio")
    private String cultivoAnterior;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getHerramientasUtilizadas() {
        return herramientasUtilizadas;
    }

    public void setHerramientasUtilizadas(String herramientasUtilizadas) {
        this.herramientasUtilizadas = herramientasUtilizadas;
    }

    public Long getIdCuadro() {
        return idCuadro;
    }

    public void setIdCuadro(Long idCuadro) {
        this.idCuadro = idCuadro;
    }

    public String getLabor() {
        return labor;
    }

    public void setLabor(String labor) {
        this.labor = labor;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Long getIdFinca() {
        return idFinca;
    }

    public void setIdFinca(Long idFinca) {
        this.idFinca = idFinca;
    }

    public String getCultivoAnterior() {
        return cultivoAnterior;
    }

    public void setCultivoAnterior(String cultivoAnterior) {
        this.cultivoAnterior = cultivoAnterior;
    }
}
