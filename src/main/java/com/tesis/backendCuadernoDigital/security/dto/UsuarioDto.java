package com.tesis.backendCuadernoDigital.security.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

public class UsuarioDto {
    @NotBlank(message = "El nombre no debe estar vacio")
    private String nombre;
    @NotBlank(message = "El apellido no debe estar vacio")
    private String apellido;
    @NotBlank(message = "El dni no debe estar vacio")
    private  String dni;
    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String nombreUsuario;
    @Email(message = "El correo es obligatorio")
    private String email;
    @NotBlank(message = "El telefono no debe estar vacio")
    private String telefono;
    @NotBlank(message = "la contraseña es obligatoria")
    private String password;
    private Set<String> roles = new HashSet<>();

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
