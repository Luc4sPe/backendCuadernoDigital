package com.tesis.backendCuadernoDigital.excepcion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@ResponseBody
public class ExcepcionRecursoNoEncontrado extends RuntimeException {
    public ExcepcionRecursoNoEncontrado(String msj){
          super(msj);
    }
}
