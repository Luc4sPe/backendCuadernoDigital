package com.tesis.backendCuadernoDigital.controller;

import com.tesis.backendCuadernoDigital.dto.AplicacionAgroquimicoDto;
import com.tesis.backendCuadernoDigital.dto.Mensaje;
import com.tesis.backendCuadernoDigital.entity.Agroquimico;
import com.tesis.backendCuadernoDigital.entity.AplicacionDeAgroquimico;
import com.tesis.backendCuadernoDigital.entity.Cuadro;
import com.tesis.backendCuadernoDigital.entity.Finca;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import com.tesis.backendCuadernoDigital.security.service.UsuarioService;
import com.tesis.backendCuadernoDigital.service.AsesoriaAgroquimicoService;
import com.tesis.backendCuadernoDigital.service.CuadroService;
import com.tesis.backendCuadernoDigital.service.FincaService;
import com.tesis.backendCuadernoDigital.service.LogService;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/asesoramientoAgroquimico")
@CrossOrigin("*")
public class AsesoriaAgroquimicoController {

    @Autowired
    AsesoriaAgroquimicoService asesoriaAgroquimicoService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    CuadroService cuadroService;

    @Autowired
    FincaService fincaService;

    @Autowired
    LogService logService;

    /*
    @PreAuthorize("hasAnyRole('ADMIN','PRODUCTOR')")
    @PostMapping("/nuevoApliAgroquimico")
    public ResponseEntity<?> crearAplicacionAgroquimico(@Valid @RequestBody AplicacionAgroquimicoDto aplicacionAgroquimicoDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal ingresados"), HttpStatus.BAD_REQUEST);

        //if (aplicacionAgroquimicoService.existeById(aplicacionAgroquimicoDto.getId()))
        //  return new ResponseEntity(new Mensaje("Esa aplicacion de agroquimico ya existe"), HttpStatus.BAD_REQUEST);


        if(aplicacionAgroquimicoDto.getDosisPorHectaria()<0)
            return new ResponseEntity(new Mensaje("La dosis por hectaria debe ser positiva"), HttpStatus.NOT_ACCEPTABLE);

        if(aplicacionAgroquimicoDto.getDosisPorHl()<0)
            return new ResponseEntity(new Mensaje("La dosis por Hl debe ser positiva"), HttpStatus.NOT_ACCEPTABLE);

        if(aplicacionAgroquimicoDto.getVolumenPorHectaria()<0)
            return new ResponseEntity(new Mensaje("El volumen por hectaria debe ser positiva"), HttpStatus.NOT_ACCEPTABLE);

        if (aplicacionAgroquimicoDto.getIdCuadro()<0)
            return new ResponseEntity(new Mensaje("El id del cuadro no puede ser negativo"), HttpStatus.BAD_REQUEST);

        if (StringUtils.isBlank(aplicacionAgroquimicoDto.getObservaciones()))
            return new ResponseEntity(new Mensaje("La observacion es obligatoria"), HttpStatus.BAD_REQUEST);

        if (aplicacionAgroquimicoDto.getIdFinca()<0)
            return new ResponseEntity(new Mensaje("El id de la finca no puede ser negativo"), HttpStatus.BAD_REQUEST);


        try {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = usuarioService.getUsuarioLogeado(auth);
            Finca finca = fincaService.getFincas(aplicacionAgroquimicoDto.getIdFinca());

            // Optional<Cuadro> cuadroOptional = cuadroService.findByIdCuadro(aplicacionAgroquimicoDto.getIdCuadro());
            //Cuadro getIdCuadro = cuadroOptional.get();
            Cuadro getIdCuadro= cuadroService.getCuadro(aplicacionAgroquimicoDto.getIdCuadro());
            Agroquimico getIdAgroquimico=agroquimicoService.getAgroquimico(aplicacionAgroquimicoDto.getIdAgroquimico());

            // Optional<Agroquimico> agroquimicoOptional = agroquimicoService.getUnAgroquimico(aplicacionAgroquimicoDto.getIdCuadro());
            //Agroquimico getIdAgroquimico = agroquimicoOptional.get();
            AplicacionDeAgroquimico aplicacionDeAgroquimico = new AplicacionDeAgroquimico(getIdAgroquimico,getIdCuadro,aplicacionAgroquimicoDto.getDosisPorHectaria(),
                    aplicacionAgroquimicoDto.getDosisPorHl(),aplicacionAgroquimicoDto.getVolumenPorHectaria(),aplicacionAgroquimicoDto.getObjetivo(),aplicacionAgroquimicoDto.getObservaciones(),"",aplicacionAgroquimicoDto.getPlaga(),finca);

            boolean resultado = aplicacionAgroquimicoService.guardarAplicaAgroquimico(aplicacionDeAgroquimico);
            if(resultado){
                logService.guardarAplicacionAgroquimico(aplicacionDeAgroquimico,usuario);
                return new ResponseEntity<>(new Mensaje("La  aplicación de agroquímico se guardado correctamente"),HttpStatus.CREATED);
            }
            return new ResponseEntity(new Mensaje(" Fallo la operacion, la  aplicación de agroquímico no registrada"), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(new Mensaje("Fallo la operacion, de aplicacion de Agroquímico no registrada"), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }
    
     */



}
