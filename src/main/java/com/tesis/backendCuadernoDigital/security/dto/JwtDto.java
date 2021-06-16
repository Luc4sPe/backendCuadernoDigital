package com.tesis.backendCuadernoDigital.security.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

// cuando hacamos un login nos devuelve el response entity del controlador un json web token, Data Transferenc Objet
public class JwtDto {

    private String token;
    private String bearer = "Bearer";
    private String nombreUsuairo;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtDto(String token, String nombreUsuairo, Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.nombreUsuairo = nombreUsuairo;
        this.authorities = authorities;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBearer() {
        return bearer;
    }

    public void setBearer(String bearer) {
        this.bearer = bearer;
    }

    public String getNombreUsuairo() {
        return nombreUsuairo;
    }

    public void setNombreUsuairo(String nombreUsuairo) {
        this.nombreUsuairo = nombreUsuairo;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
