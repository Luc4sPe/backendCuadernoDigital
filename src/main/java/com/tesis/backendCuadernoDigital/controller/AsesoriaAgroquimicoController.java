package com.tesis.backendCuadernoDigital.controller;


import com.tesis.backendCuadernoDigital.dto.AsesoriaAgroquimicoDto;
import com.tesis.backendCuadernoDigital.dto.Mensaje;
import com.tesis.backendCuadernoDigital.dto.ModificarAsesoriaAgroquimicoDto;
import com.tesis.backendCuadernoDigital.entity.*;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import com.tesis.backendCuadernoDigital.security.service.UsuarioService;
import com.tesis.backendCuadernoDigital.service.*;
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
import java.util.List;
import java.util.Optional;

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

    @Autowired
    AgroquimicoService agroquimicoService;


    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @PostMapping("/nuevoAsesoriaAgroquimico")
    public ResponseEntity<?> crearAsesoriaAgroquimico(@Valid @RequestBody AsesoriaAgroquimicoDto asesoriaAgroquimicoDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal ingresados"), HttpStatus.BAD_REQUEST);

        if(asesoriaAgroquimicoDto.getDosisPorHectaria()<0)
            return new ResponseEntity(new Mensaje("La dosis por hectaria debe ser positiva"), HttpStatus.NOT_ACCEPTABLE);

        if(asesoriaAgroquimicoDto.getDosisPorHl()<0)
            return new ResponseEntity(new Mensaje("La dosis por Hl debe ser positiva"), HttpStatus.NOT_ACCEPTABLE);

        if(asesoriaAgroquimicoDto.getVolumenPorHectaria()<0)
            return new ResponseEntity(new Mensaje("El volumen por hectaria debe ser positiva"), HttpStatus.NOT_ACCEPTABLE);

        if (asesoriaAgroquimicoDto.getIdCuadro()<0)
            return new ResponseEntity(new Mensaje("El id del cuadro no puede ser negativo"), HttpStatus.BAD_REQUEST);

        if (StringUtils.isBlank(asesoriaAgroquimicoDto.getObjetivo()))
            return new ResponseEntity(new Mensaje("El objetivo es obligatoria"), HttpStatus.BAD_REQUEST);

        if (asesoriaAgroquimicoDto.getIdFinca()<0)
            return new ResponseEntity(new Mensaje("El id de la finca no puede ser negativo"), HttpStatus.BAD_REQUEST);


        try {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = usuarioService.getUsuarioLogeado(auth);
            Finca finca = fincaService.getFincas(asesoriaAgroquimicoDto.getIdFinca());

            Agroquimico getIdAgroquimico=agroquimicoService.getAgroquimico(asesoriaAgroquimicoDto.getIdAgroquimico());
            Cuadro getIdCuadro= cuadroService.getCuadro(asesoriaAgroquimicoDto.getIdCuadro());

            Optional<Usuario> usuarioOptional = usuarioService.getByNombreUsuario(asesoriaAgroquimicoDto.getNombreProductor());
            Usuario usuarioCapturado = usuarioOptional.get();

            AsesoriaAgroquimico asesoriaAgroquimico = new AsesoriaAgroquimico(getIdAgroquimico,getIdCuadro,asesoriaAgroquimicoDto.getDosisPorHectaria(),
                    asesoriaAgroquimicoDto.getDosisPorHl(),asesoriaAgroquimicoDto.getVolumenPorHectaria(),asesoriaAgroquimicoDto.getObjetivo(),asesoriaAgroquimicoDto.getPlaga(),asesoriaAgroquimicoDto.getFechaEstimadaAplicacion(),finca,usuarioCapturado);

            boolean resultado = asesoriaAgroquimicoService.guardarAsesoramientoAgroquimico(asesoriaAgroquimico);

            if(resultado){
                logService.guardarAsesoriaAgroquimico(asesoriaAgroquimico,usuario);
                return new ResponseEntity<>(new Mensaje("La asesoria de la aplicación de agroquímico se guardado correctamente"),HttpStatus.CREATED);
            }
            return new ResponseEntity(new Mensaje(" Fallo la operacion, la asesoria de la aplicación de agroquímico no se registro"), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity(new Mensaje("Fallo la operacion, la asesoria de la aplicación de agroquímico no se registro"), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }




    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @GetMapping("/listaAsesoriaAgroquimico")
    public ResponseEntity<List<AsesoriaAgroquimico>> listaAsesoriaAgroquimico(){
        List<AsesoriaAgroquimico> listar = asesoriaAgroquimicoService.listarAsesoriaAgroquimico();
        return new ResponseEntity<>(listar,HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> modificarAsesoriaAgroquimico(@PathVariable("id") Long id, @Valid @RequestBody ModificarAsesoriaAgroquimicoDto modificarAsesoriaAgroquimicoDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("Campos mal ingresado"), HttpStatus.BAD_REQUEST);

        if (!asesoriaAgroquimicoService.existeByIdAsesoriaAgroquimico(id))
            return new ResponseEntity(new Mensaje("no existe esa asesoria de agroquímico"), HttpStatus.NOT_FOUND);

        if(modificarAsesoriaAgroquimicoDto.getDosisPorHl()<0)
            return new ResponseEntity(new Mensaje("La dosis por Hl debe ser positiva"), HttpStatus.NOT_ACCEPTABLE);

        if(modificarAsesoriaAgroquimicoDto.getVolumenPorHectaria()<0)
            return new ResponseEntity(new Mensaje("El volumen por hectaria debe ser positiva"), HttpStatus.NOT_ACCEPTABLE);

        if (modificarAsesoriaAgroquimicoDto.getIdCuadro()<0)
            return new ResponseEntity(new Mensaje("El id del cuadro no puede ser negativo"), HttpStatus.BAD_REQUEST);

        //se obtiene una asesoría a traves del id para manupular uno de los atributo de la clase en este caso el estado
        AsesoriaAgroquimico asesoriaAgroquimico = asesoriaAgroquimicoService.getAsesoriaAgroquimico(id);

        if(asesoriaAgroquimico.isAsesoriaAplicada()==true)
            return new ResponseEntity(new Mensaje("La asesoria fue aplicada no se puede modificar "), HttpStatus.BAD_REQUEST);

        if(asesoriaAgroquimico.getFechaModificacionAsesoriaAgroquimico()!=null)
            return new ResponseEntity(new Mensaje("La asesoría ya fue modificada "), HttpStatus.BAD_REQUEST);

        try {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = usuarioService.getUsuarioLogeado(auth);

            Cuadro getCuadro= cuadroService.getCuadro(modificarAsesoriaAgroquimicoDto.getIdCuadro());
            Agroquimico getAgroquimico=agroquimicoService.getAgroquimico(modificarAsesoriaAgroquimicoDto.getIdAgroquimico());


            AsesoriaAgroquimico modificarAsesoriaAgroquimico = asesoriaAgroquimicoService.getUnaAsesoriaAgroquimico(id).get();
            modificarAsesoriaAgroquimico.setAgroquimico(getAgroquimico);
            modificarAsesoriaAgroquimico.setCuadro(getCuadro);
            modificarAsesoriaAgroquimico.setDosisPorHectaria(modificarAsesoriaAgroquimicoDto.getDosisPorHectaria());
            modificarAsesoriaAgroquimico.setDosisPorHl(modificarAsesoriaAgroquimicoDto.getDosisPorHl());
            modificarAsesoriaAgroquimico.setVolumenPorHectaria(modificarAsesoriaAgroquimicoDto.getVolumenPorHectaria());
            modificarAsesoriaAgroquimico.setObjetivo(modificarAsesoriaAgroquimicoDto.getObjetivo());
            modificarAsesoriaAgroquimico.setPlaga(modificarAsesoriaAgroquimicoDto.getPlaga());
            modificarAsesoriaAgroquimico.fechaModificacionAsesoriaAgroquimico();
            modificarAsesoriaAgroquimico.setFechaEstimadaAplicacion(modificarAsesoriaAgroquimicoDto.getFechaEstimadaAplicacion());

            asesoriaAgroquimicoService.actualizarAsesoriaAgroquimico(modificarAsesoriaAgroquimico);

            if(modificarAsesoriaAgroquimico!=null){

                logService.modificarAsesoriaAgroquimico(modificarAsesoriaAgroquimico,usuario);
                return new ResponseEntity<>(new Mensaje(" Asesoría de aplicación de agroquímico actualizada correctamente"), HttpStatus.OK);
            }
            return new ResponseEntity(new Mensaje("Asesoría de aplicación de agroquímico no se actualizó correctamente"),HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity(new Mensaje("Fallo la operacion, asesoría de aplicación de agroquímico"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }




    @PreAuthorize("hasAnyRole('ADMIN','ENCARGADO_AGRICOLA')")
    @GetMapping("/detalle/{id}")
    ResponseEntity<AsesoriaAgroquimico> obteberDetalleDeUnaAsesoriaAgroqui(@PathVariable("id") Long id){
        if(!asesoriaAgroquimicoService.existeByIdAsesoriaAgroquimico(id))
            return new ResponseEntity(new Mensaje("no existe esa asesoría"),HttpStatus.NOT_FOUND);
        AsesoriaAgroquimico asesoria = asesoriaAgroquimicoService.getUnaAsesoriaAgroquimico(id).get();
        return new ResponseEntity(asesoria,HttpStatus.OK);
    }







}
