package com.tesis.backendCuadernoDigital.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CultivoDto {

    @NotBlank(message = "El nombre de cultivo no debe estar vacio")
    private String nombre;
    @NotBlank(message = "El remito no debe estar vacio")
    private String remito;

    @NotBlank(message = "La variedad no debe estar vacio")
    private String variedadCultivo;
    @NotBlank(message = "El vivero proveedor no debe estar vacio")
    private String viveroProvedor;

    @NotNull(message = "El tiempo de cultivo no debe estar vacio")
    @Min(value = 1, message = "El minimo valor del tiempo de cultivo es 1")
    private int tiempoDeCultivo;


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRemito() {
        return remito;
    }

    public void setRemito(String remito) {
        this.remito = remito;
    }

    public String getVariedadCultivo() {
        return variedadCultivo;
    }

    public void setVariedadCultivo(String variedadCultivo) {
        this.variedadCultivo = variedadCultivo;
    }

    public String getViveroProvedor() {
        return viveroProvedor;
    }

    public void setViveroProvedor(String viveroProvedor) {
        this.viveroProvedor = viveroProvedor;
    }

    public int getTiempoDeCultivo() {
        return tiempoDeCultivo;
    }

    public void setTiempoDeCultivo(int tiempoDeCultivo) {
        this.tiempoDeCultivo = tiempoDeCultivo;
    }
}
