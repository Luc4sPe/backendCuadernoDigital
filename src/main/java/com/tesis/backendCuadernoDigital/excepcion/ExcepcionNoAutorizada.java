package com.tesis.backendCuadernoDigital.excepcion;

public class ExcepcionNoAutorizada extends RuntimeException {
    public ExcepcionNoAutorizada(String msj){
        super(msj);
    }
}
