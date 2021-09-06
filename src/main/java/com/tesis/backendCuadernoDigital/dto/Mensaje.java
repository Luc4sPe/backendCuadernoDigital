package com.tesis.backendCuadernoDigital.dto;

public class Mensaje {
    private String mensaje;

    public Mensaje(String msj) {
        this.mensaje = msj;
    }

    public String getMensaje() {
        return this.mensaje;
    }

    public void setMensaje(String msj) {
        this.mensaje = msj;
    }
}
