package com.tesis.backendCuadernoDigital.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.Date;

public class EditarRiegoDto {
    @NotNull(message = "El id no puede ser un valor nulo")
    @Min(value = 1, message = "El minimo de entre ileras es 1")
    private Long id;
    @NotBlank
    private LocalTime duracionEnHoras;
    @NotBlank
    private float milimetrosAplicados;
    @NotNull(message = "El id no puede ser un valor nulo")
    @Min(value = 1, message = "El minimo de entre ileras es 1")
    private Long cuadro;
    @NotBlank
    private String observacionProductor;

    @NotBlank
    private String justificacionProductor;

    // @NotBlank
    //private int semanaTransplante;

    @NotNull(message = "La finca es obligatoria ")
    private Long idFinca;

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

    public Long getCuadro() {
        return cuadro;
    }

    public void setCuadro(Long cuadro) {
        this.cuadro = cuadro;
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

    public Long getIdFinca() {
        return idFinca;
    }

    public void setIdFinca(Long idFinca) {
        this.idFinca = idFinca;
    }
}
