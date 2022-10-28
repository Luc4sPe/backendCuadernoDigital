package com.tesis.backendCuadernoDigital.security.dto;

import javax.validation.constraints.NotBlank;

public class CambioPasswordDto {

    @NotBlank(message = "La contraseña actual no debe estar vacia")
    private String passwordActual;
    @NotBlank(message = "La contraseña nueva no debe estar vacia")
    private String passwordNuevo;
    @NotBlank(message = "Confirmar la contraseña es obligatorio")
    private String confirmarPassword;

    public String getPasswordActual() {
        return passwordActual;
    }

    public void setPasswordActual(String passwordActual) {
        this.passwordActual = passwordActual;
    }

    public String getPasswordNuevo() {
        return passwordNuevo;
    }

    public void setPasswordNuevo(String passwordNuevo) {
        this.passwordNuevo = passwordNuevo;
    }

    public String getConfirmarPassword() {
        return confirmarPassword;
    }

    public void setConfirmarPassword(String confirmarPassword) {
        this.confirmarPassword = confirmarPassword;
    }
}
