package com.tesis.backendCuadernoDigital.excepcion;

public class ExcepcionNoAutorizada extends RuntimeException {
    public ExcepcionNoAutorizada(String mensaje){
        super((mensaje));
    }
}
