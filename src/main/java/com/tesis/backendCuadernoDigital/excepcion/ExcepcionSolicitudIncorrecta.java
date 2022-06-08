package com.tesis.backendCuadernoDigital.excepcion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@ResponseBody
public class ExcepcionSolicitudIncorrecta extends RuntimeException {
    public ExcepcionSolicitudIncorrecta(String msj){
        super(msj);
    }
}
