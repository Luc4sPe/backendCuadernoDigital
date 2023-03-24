package com.tesis.backendCuadernoDigital.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

public class ModificarAsesoriaRiegoDto {

    @NotNull(message = "El id no puede ser un valor nulo")
    @Min(value = 1, message = "El minimo de entre ileras es 1")
    private Long id;
    @NotBlank
    private LocalTime duracionEnHoras;
    @NotBlank
    private float milimetrosAplicados;
    @NotNull(message = "El id no puede ser un valor nulo")
    @Min(value = 1, message = "El minimo es 1")
    private Long idCuadro;
    @NotNull(message = "La finca es obligatoria ")
    private Long idFinca;
    @Min(value = 1, message = "El minimo es 1")
    private Long idProductor;

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

    public Long getIdCuadro() {
        return idCuadro;
    }

    public void setIdCuadro(Long idCuadro) {
        this.idCuadro = idCuadro;
    }

    public Long getIdFinca() {
        return idFinca;
    }

    public void setIdFinca(Long idFinca) {
        this.idFinca = idFinca;
    }

    public Long getIdProductor() {
        return idProductor;
    }

    public void setIdProductor(Long idProductor) {
        this.idProductor = idProductor;
    }
}
