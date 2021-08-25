package com.tesis.backendCuadernoDigital.security.entity;
//acceso a la base de datos
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Usuario {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private int id;
     @NotNull
     private String nombre;
     @NotNull
     private String apellido;
     @NotNull
     @Column(unique = true)
     private String nombreUsuario;
     @NotNull
     private String email;
     @NotNull
     private String password;
     private String TokenPassword;
     @NotNull
     @ManyToMany
     @JoinTable(name="usuario_rol", joinColumns = @JoinColumn(name="id_usuario"),
     inverseJoinColumns = @JoinColumn(name = "id_rol"))
     private Set<Rol> roles = new HashSet<>();

     public Usuario() {
     }

     public Usuario(@NotNull String nombre,@NotNull String apellido,@NotNull String nombreUsuario,@NotNull String email,@NotNull String password) {
          this.nombre = nombre;
          this.apellido = apellido;
          this.nombreUsuario = nombreUsuario;
          this.email = email;
          this.password = password;
     }

     public int getId() {
          return id;
     }

     public void setId(int id) {
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

     public String getPassword() {
          return password;
     }

     public void setPassword(String password) {
          this.password = password;
     }

     public String getTokenPassword() {
          return TokenPassword;
     }

     public void setTokenPassword(String tokenPassword) {
          TokenPassword = tokenPassword;
     }

     public Set<Rol> getRoles() {
          return roles;
     }

     public void setRoles(Set<Rol> roles) {
          this.roles = roles;
     }
}
