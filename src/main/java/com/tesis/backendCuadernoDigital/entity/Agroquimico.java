package com.tesis.backendCuadernoDigital.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Agroquimico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String formulaYconcentracion;
    @NotNull
    private String lote;
    @NotNull
    private String nombreComun;
    @NotNull
    private String observaciones;
    @NotNull
    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JsonIgnoreProperties("agroquimico")
    @JoinColumn(name = "Plagas")
    private Plaga plaga;
    @NotNull
    private String principioActivo;
    @NotNull
    private int tiempoDeCarencia;
    @NotNull
    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JsonIgnoreProperties("agroquimico")
    @JoinColumn(name = "TiepoDeAgroquimicos")
    private TipoAgroquimico tipoAgroquimico;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn (name = "EncarcadoAgricola")
    private Usuario nombreEncargadoAgricola;

    @OneToMany(mappedBy = "agroquimico", fetch =  FetchType.EAGER)
    private List<AplicacionDeAgroquimico> aplicacionAgroquimico = new ArrayList<AplicacionDeAgroquimico>();




    public Agroquimico() {
    }

    public Agroquimico(@NotNull String formulaYconcentracion, @NotNull String lote, @NotNull String nombreComun, @NotNull String observaciones, @NotNull Plaga plaga, @NotNull String principioActivo, @NotNull int tiempoDeCarencia, @NotNull TipoAgroquimico tipoAgroquimico, Usuario nombreEncargadoAgricola) {
        this.formulaYconcentracion = formulaYconcentracion;
        this.lote = lote;
        this.nombreComun = nombreComun;
        this.observaciones = observaciones;
        this.plaga = plaga;
        this.principioActivo = principioActivo;
        this.tiempoDeCarencia = tiempoDeCarencia;
        this.tipoAgroquimico = tipoAgroquimico;
        this.nombreEncargadoAgricola = nombreEncargadoAgricola;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFormulaYconcentracion() {
        return formulaYconcentracion;
    }

    public void setFormulaYconcentracion(String formulaYconcentracion) {
        this.formulaYconcentracion = formulaYconcentracion;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getNombreComun() {
        return nombreComun;
    }

    public void setNombreComun(String nombreComun) {
        this.nombreComun = nombreComun;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Plaga getPlaga() {
        return plaga;
    }

    public void setPlaga(Plaga plaga) {
        this.plaga = plaga;
    }

    public String getPrincipioActivo() {
        return principioActivo;
    }

    public void setPrincipioActivo(String principioActivo) {
        this.principioActivo = principioActivo;
    }

    public int getTiempoDeCarencia() {
        return tiempoDeCarencia;
    }

    public void setTiempoDeCarencia(int tiempoDeCarencia) {
        this.tiempoDeCarencia = tiempoDeCarencia;
    }

    public TipoAgroquimico getTipoAgroquimico() {
        return tipoAgroquimico;
    }

    public void setTipoAgroquimico(TipoAgroquimico tipoAgroquimico) {
        this.tipoAgroquimico = tipoAgroquimico;
    }

    public Usuario getNombreEncargadoAgricola() {
        return nombreEncargadoAgricola;
    }

    public void setNombreEncargadoAgricola(Usuario nombreEncargadoAgricola) {
        this.nombreEncargadoAgricola = nombreEncargadoAgricola;
    }

    public List<AplicacionDeAgroquimico> getAplicacionAgroquimico() {
        return aplicacionAgroquimico;
    }

    public void setAplicacionAgroquimico(List<AplicacionDeAgroquimico> aplicacionAgroquimico) {
        this.aplicacionAgroquimico = aplicacionAgroquimico;
    }
}
