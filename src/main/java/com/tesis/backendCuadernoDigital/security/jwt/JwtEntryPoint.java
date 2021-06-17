package com.tesis.backendCuadernoDigital.security.jwt;
//comprueba si hay un token valido

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {

    // Método q se implementa para saber cual es el Metodo q nos esta dando error en caso de q no funcione la app
    private final static Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException e) throws IOException, ServletException {
        logger.error("Falla en el método commence verificar ");
        res.sendError(HttpServletResponse.SC_UNAUTHORIZED,"No autorizado");
    }
}
