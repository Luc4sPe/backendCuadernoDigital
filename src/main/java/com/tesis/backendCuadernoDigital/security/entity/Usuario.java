package com.tesis.backendCuadernoDigital.security.entity;
//acceso a la base de datos
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Usuario {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     @NotNull
     private String nombre;

     @NotNull
     private String apellido;

     @NotNull
     private String dni;

     @NotNull
     @Column(unique = true)
     private String nombreUsuario;

     @NotNull
     @Column(unique = true)
     private String email;

     @NotNull
     @Column(unique = true)
     private String telefono;
     @NotNull
     private String password;

     @NotNull
     @ManyToMany (fetch = FetchType.EAGER)
     @JoinTable(name="usuario_rol", joinColumns = @JoinColumn(name="id_usuario"),
     inverseJoinColumns = @JoinColumn(name = "id_rol"))
     private Set<Rol> roles = new HashSet<>();

     @NotNull
     private boolean estadoActivo;

     @CreationTimestamp
     private Date fechaDeAlta;

     @UpdateTimestamp
     private Date fechaModificacion;

     private String tokenPassword;

     public Usuario() {
     }

     public Usuario(@NotNull String nombre,@NotNull String apellido,@NotNull String dni,@NotNull String nombreUsuario,@NotNull String email,@NotNull String telefono,@NotNull String password) {
          this.nombre = nombre;
          this.apellido = apellido;
          this.dni = dni;
          this.nombreUsuario = nombreUsuario;
          this.email = email;
          this.telefono=telefono;
          this.password = password;
          this.estadoActivo=true;
          this.fechaDeAlta=null;
          this.fechaModificacion=null;

     }

     public Long getId() {
          return id;
     }

     public void setId(Long id) {
          this.id = id;
     }

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


     public boolean isEstadoActivo() {
          return estadoActivo;
     }

     public void setEstadoActivo(boolean estadoActivo) {
          this.estadoActivo = estadoActivo;
     }

     public void modificarEstado(){
        this.estadoActivo=!isEstadoActivo();

    }

     public Date getFechaDeAlta() {
          return fechaDeAlta;
     }

     public void setFechaDeAlta(Date fechaDeAlta) {
          this.fechaDeAlta = fechaDeAlta;
     }

     public Date getFechaModificacion() {
          return fechaModificacion;
     }

     public void setFechaModificacion(Date fechaModificacion) {
          this.fechaModificacion = fechaModificacion;
     }

     public Set<Rol> getRoles() {
          return roles;
     }

     public void setRoles(Set<Rol> roles) {
          this.roles = roles;
     }



     public String getTokenPassword() {
          return tokenPassword;
     }

     public void setTokenPassword(String tokenPassword) {
          this.tokenPassword = tokenPassword;
     }
}
