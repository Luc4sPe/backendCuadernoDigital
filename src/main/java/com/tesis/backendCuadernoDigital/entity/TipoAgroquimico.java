package com.tesis.backendCuadernoDigital.entity;
/*
import com.tesis.backendCuadernoDigital.security.entity.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TipoAgroquimico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Column(unique = true)
    private String nombre;
    @NotNull
    private String descripcion;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn (name = "EncarcadoAgricola")
    private Usuario nombreEncargadoAgricola;

    @OneToMany(mappedBy = "tipoAgroquimico", fetch =  FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Agroquimico> agroquimico = new ArrayList<Agroquimico>();

    public TipoAgroquimico() {
    }

    public TipoAgroquimico(@NotNull String nombre, @NotNull String descripcion, Usuario nombreEncargadoAgricola) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.nombreEncargadoAgricola = nombreEncargadoAgricola;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Usuario getNombreEncargadoAgricola() {
        return nombreEncargadoAgricola;
    }

    public void setNombreEncargadoAgricola(Usuario nombreEncargadoAgricola) {
        this.nombreEncargadoAgricola = nombreEncargadoAgricola;
    }

    public List<Agroquimico> getAgroquimico() {
        return agroquimico;
    }

    public void setAgroquimico(List<Agroquimico> agroquimico) {
        this.agroquimico = agroquimico;
    }
}

 */
