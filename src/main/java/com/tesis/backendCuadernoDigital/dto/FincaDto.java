package com.tesis.backendCuadernoDigital.dto;


import com.tesis.backendCuadernoDigital.entity.Cuadro;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class FincaDto {

    @NotBlank(message = "El campo Sistema de Trasplante no puede estar vacio")
    private String nombre;

    @NotBlank(message = "El campo Sistema de Trasplante no puede estar vacio")
    private String direccion;

    @NotNull(message = "No puede ser un valor nulo")
    @Min(value = 1, message = "El minimo de entre ileras es 1")
    private float longitud;

    @NotNull(message = "No puede ser un valor nulo")
    @Min(value = 1, message = "El minimo de entre ileras es 1")
    private float latitud;

    @NotEmpty(message = "La lista no puede estar vacia")
    private List<Cuadro> cuadros = new ArrayList<>();

    @NotBlank(message = "El campo Sistema de Trasplante no puede estar vacio")
    private String nombreProductor;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public List<Cuadro> getCuadros() {
        return cuadros;
    }

    public void setCuadros(List<Cuadro> cuadros) {
        this.cuadros = cuadros;
    }

    public String getNombreProductor() {
        return nombreProductor;
    }

    public void setNombreProductor(String nombreProductor) {
        this.nombreProductor = nombreProductor;
    }
}
