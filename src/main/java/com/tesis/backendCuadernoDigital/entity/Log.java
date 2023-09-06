package com.tesis.backendCuadernoDigital.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tesis.backendCuadernoDigital.enums.LogAccion;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import org.hibernate.annotations.CreationTimestamp;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Enumeration;

@Entity
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private Date fecha;

    @NotNull
    @ManyToOne
    @JsonIgnoreProperties("roles")
    private Usuario usuario;

    @NotNull
    @Enumerated(EnumType.STRING)
    private LogAccion logAccion;

    @NotNull
    private String descripcion;

    @NotNull
    private Long objeto_id;

    @NotNull
    String macc;

    public Log() {
    }

    public Log(@NotNull Usuario usuario, @NotNull LogAccion logAccion, @NotNull String descripcion, @NotNull Long objeto_id) {
        this.fecha = null;
        this.usuario = usuario;
        this.logAccion = logAccion;
        this.descripcion = descripcion;
        this.objeto_id = objeto_id;

    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LogAccion getLogAccion() {
        return logAccion;
    }

    public void setLogAccion(LogAccion logAccion) {
        this.logAccion = logAccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getObjeto_id() {
        return objeto_id;
    }

    public void setObjeto_id(Long objeto_id) {
        this.objeto_id = objeto_id;
    }

    public String getMacc() {
        return macc;
    }

    public void setMacc(String macc) {
        this.macc = macc;
    }

    public void obtenerMacc()  {
        String mac = null;

        try {
            InetAddress ip = InetAddress.getLocalHost();
            Enumeration e = NetworkInterface.getNetworkInterfaces();

            while(e.hasMoreElements()) {

                NetworkInterface n = (NetworkInterface) e.nextElement();
                Enumeration ee = n.getInetAddresses();
                while(ee.hasMoreElements()) {
                    InetAddress i = (InetAddress) ee.nextElement();
                    if(!i.isLoopbackAddress() && !i.isLinkLocalAddress() && i.isSiteLocalAddress()) {
                        ip = i;
                    }
                }
            }

            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac_byte = network.getHardwareAddress();

            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < mac_byte.length; i++) {
                sb.append(String.format("%02X%s", mac_byte[i], (i < mac_byte.length -1) ? "-" : ""));
            }
           // mac = sb.toString();
          //  System.out.println(mac);
            this.setMacc(sb.toString());
        } catch (UnknownHostException e) {

        e.printStackTrace();

    } catch (SocketException e){

        e.printStackTrace();

    }
        }


}
