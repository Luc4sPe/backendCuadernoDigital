package com.tesis.backendCuadernoDigital.security.dto;


// cuando hacamos un login nos devuelve el response entity del controlador un json web token, Data Transferenc Objet
public class JwtDto {

    private String token;
    /* Se corrigieron errores de vulnerabilidad, ahora solo enviamos el token
        el front deberá recuperar el username y roles mediante el token
    private String bearer = "Bearer";
    private String nombreUsuairo;
    private Collection<? extends GrantedAuthority> authorities;
    */

    public JwtDto() {
    }

    public JwtDto(String token) {
        this.token = token;
       // this.nombreUsuairo = nombreUsuairo;
        //this.authorities = authorities;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
