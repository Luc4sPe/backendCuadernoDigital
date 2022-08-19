package com.tesis.backendCuadernoDigital.entity;

import com.tesis.backendCuadernoDigital.security.entity.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Finca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFinca;
    @NotNull
    @Column(unique = true)
    private String nombre;
    @NotNull
    private String direccion;

    @NotNull
    private float longitud;
    @NotNull
    private float latitud;

    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "Cuadro")
    private Set<Cuadro> cuadros = new HashSet<>();

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "nombreUsuarioProductor")
    private Usuario productor;

    public Finca() {
    }

    public Long getIdFinca() {
        return idFinca;
    }

    public void setIdFinca(Long idFinca) {
        this.idFinca = idFinca;
    }

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

    public Set<Cuadro> getCuadros() {
        return cuadros;
    }

    public void setCuadros(Set<Cuadro> cuadros) {
        this.cuadros = cuadros;
    }

    public Usuario getProductor() {
        return productor;
    }

    public void setProductor(Usuario productor) {
        this.productor = productor;
    }
}
