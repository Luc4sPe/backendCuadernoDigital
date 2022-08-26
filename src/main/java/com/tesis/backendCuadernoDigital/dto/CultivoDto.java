package com.tesis.backendCuadernoDigital.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class CultivoDto {

    @NotBlank(message = "El nombre de cultivo no debe estar vacio")
    private String nombre;
    @NotBlank(message = "El remito no debe estar vacio")
    private String remito;
    @NotBlank(message = "El tipo de carencia no debe estar vacio")
    @Min(value = 1, message = "El minimo de tiempo de carencia es 1")
    private int timpoCarencia;
    @NotBlank(message = "La variedad no debe estar vacio")
    private String variedadCultivo;
    @NotBlank(message = "El vivero proveedor no debe estar vacio")
    private String viveroProvedor;

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

    public int getTimpoCarencia() {
        return timpoCarencia;
    }

    public void setTimpoCarencia(int timpoCarencia) {
        this.timpoCarencia = timpoCarencia;
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
}
