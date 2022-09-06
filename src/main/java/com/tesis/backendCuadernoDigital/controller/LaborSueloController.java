package com.tesis.backendCuadernoDigital.controller;


import com.tesis.backendCuadernoDigital.dto.LaborsueloDto;
import com.tesis.backendCuadernoDigital.dto.Mensaje;
import com.tesis.backendCuadernoDigital.dto.ModificarLaborSueloDto;
import com.tesis.backendCuadernoDigital.entity.Cuadro;
import com.tesis.backendCuadernoDigital.entity.LaborSuelo;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import com.tesis.backendCuadernoDigital.security.service.UsuarioService;
import com.tesis.backendCuadernoDigital.service.CuadroService;
import com.tesis.backendCuadernoDigital.service.LaborSueloService;
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
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/laborSuelo")
@CrossOrigin("*")
public class LaborSueloController {

    @Autowired
    UsuarioService usuarioService;
    @Autowired
    LogService logService;
    @Autowired
    CuadroService cuadroService;
    @Autowired
    LaborSueloService laborSueloService;

    @PreAuthorize("hasAnyRole('PRODUCTOR')")
    @PostMapping("/crearLabor")
    public ResponseEntity<?> crearLaborDesuelo(@Valid @RequestBody LaborsueloDto laborsueloDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal ingresados"), HttpStatus.BAD_REQUEST);

        if (laborSueloService.existsByIdLabor(laborsueloDto.getId()))
            return new ResponseEntity(new Mensaje("Esa labor ya existe"), HttpStatus.BAD_REQUEST);

        if (StringUtils.isBlank(laborsueloDto.getCultivoAnterior()))
            return new ResponseEntity(new Mensaje("El cultivo es obligatoria"), HttpStatus.BAD_REQUEST);

        if (StringUtils.isBlank(laborsueloDto.getHerramientasUtilizadas()))
            return new ResponseEntity(new Mensaje("La Herramienta es obligatoria"), HttpStatus.BAD_REQUEST);

        if (laborsueloDto.getIdCuadro()<0)
            return new ResponseEntity(new Mensaje("El id del cuadro no puede ser negativo"), HttpStatus.BAD_REQUEST);

        if (StringUtils.isBlank(laborsueloDto.getLabor()))
            return new ResponseEntity(new Mensaje("La observacion es obligatoria"), HttpStatus.BAD_REQUEST);

        if (StringUtils.isBlank(laborsueloDto.getObservacion()))
            return new ResponseEntity(new Mensaje("La observacion es obligatoria"), HttpStatus.BAD_REQUEST);

        try {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = usuarioService.getUsuarioLogeado(auth);
            Optional<Cuadro> cuadroOptional = cuadroService.getCuadro(laborsueloDto.getIdCuadro());
            Cuadro cuadroEnviar = cuadroOptional.get();
            LaborSuelo nuevaLabor = new LaborSuelo(laborsueloDto.getCultivoAnterior(),laborsueloDto.getHerramientasUtilizadas(),
                    cuadroEnviar,laborsueloDto.getLabor(),laborsueloDto.getObservacion(),"");
              laborSueloService.guardarLaborSuelo(nuevaLabor);
            if (nuevaLabor!=null){
                logService.guardarLaborSuelo(nuevaLabor,usuario);
                return new ResponseEntity<>(new Mensaje("La Labro de Suelo se guardado correctamente"), HttpStatus.CREATED);
            }
            return new ResponseEntity(new Mensaje("Fallo la operacion, Labor no Registrada"), HttpStatus.INTERNAL_SERVER_ERROR);

        }catch (Exception e){
            return new ResponseEntity(new Mensaje("Fallo la operacion, Labor no Registrada"), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @PreAuthorize("hasAnyRole('PRODUCTOR')")
    @GetMapping("/listadoLabor")
    public ResponseEntity<List<LaborSuelo>> listadoLaborSuelo(){
        List<LaborSuelo> listado = laborSueloService.listarLabores();
        return new ResponseEntity<>(listado, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('PRODUCTOR')")
    @GetMapping("/laborPorCuador/{idCuadro}")
    public ResponseEntity<List<LaborSuelo>> listadoLaborPorCuadro(@PathVariable ("idCuadro") Long idCuadro){
        List<LaborSuelo> listadoPorCuadro = laborSueloService.listadoLaborPorCuadro(idCuadro);
        return new ResponseEntity<>(listadoPorCuadro,HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('PRODUCTOR')")
    @PutMapping("/modificarLabor/{id}")
    public ResponseEntity<?> modificarLabor(@PathVariable ("id") Long id, @Valid @RequestBody ModificarLaborSueloDto modificarLaborSueloDto, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal ingresados"), HttpStatus.BAD_REQUEST);

        if (!laborSueloService.existsByIdLabor(id))
            return new ResponseEntity(new Mensaje("Esa labor no existe"), HttpStatus.BAD_REQUEST);

        if (StringUtils.isBlank(modificarLaborSueloDto.getCultivoAnterior()))
            return new ResponseEntity(new Mensaje("El cultivo es obligatoria"), HttpStatus.BAD_REQUEST);

        if (StringUtils.isBlank(modificarLaborSueloDto.getHerramientasUtilizadas()))
            return new ResponseEntity(new Mensaje("La Herramienta es obligatoria"), HttpStatus.BAD_REQUEST);

        if (modificarLaborSueloDto.getIdCuadro()<0)
            return new ResponseEntity(new Mensaje("El id del cuadro no puede ser negativo"), HttpStatus.BAD_REQUEST);

        if (StringUtils.isBlank(modificarLaborSueloDto.getLabor()))
            return new ResponseEntity(new Mensaje("La observacion es obligatoria"), HttpStatus.BAD_REQUEST);

        if (StringUtils.isBlank(modificarLaborSueloDto.getObservacion()))
            return new ResponseEntity(new Mensaje("La observacion es obligatoria"), HttpStatus.BAD_REQUEST);

        if (StringUtils.isBlank(modificarLaborSueloDto.getJustificacion()))
            return new ResponseEntity(new Mensaje("Tiene que declarar una justificación"), HttpStatus.BAD_REQUEST);

        Optional<LaborSuelo> laborSueloOptional = laborSueloService.getLaborSuelo(id);
        LaborSuelo justificacion = laborSueloOptional.get();

        if (!justificacion.getJustificacion().isEmpty())
            return new ResponseEntity(new Mensaje("El archivo ya ha sido modificado anteriormente "), HttpStatus.BAD_REQUEST);

        Optional<Cuadro> cuadroOptional = cuadroService.getCuadro(modificarLaborSueloDto.getIdCuadro());
        Cuadro getIdCuadro = cuadroOptional.get();

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = usuarioService.getUsuarioLogeado(auth);
            LaborSuelo modificarLabor = laborSueloService.getLaborSuelo(id).get();
            modificarLabor.setCultivoAnterior(modificarLabor.getCultivoAnterior());
            modificarLabor.setHerramientasUtilizadas(modificarLaborSueloDto.getHerramientasUtilizadas());
            modificarLabor.setIdCuadro(getIdCuadro);
            modificarLabor.setLabor(modificarLaborSueloDto.getLabor());
            modificarLabor.setObservacion(modificarLaborSueloDto.getObservacion());
            modificarLabor.setJustificacion(modificarLaborSueloDto.getJustificacion());
            laborSueloService.modificarLabor(modificarLabor);
            if(modificarLabor!=null){
                logService.modificarLaborSuelo(modificarLabor,usuario);
                return new ResponseEntity<>(new Mensaje(" Labor actualizada correctamente"), HttpStatus.OK);
            }
            return new ResponseEntity(new Mensaje("Fallo la operacion, Labor no Registrada"), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e){
            return new ResponseEntity(new Mensaje("Fallo la operacion, Labor no Registrada"), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @PreAuthorize("hasAnyRole('PRODUCTOR')")
    @GetMapping("/CantidadCuadroDeFinca")
    public ResponseEntity<Integer> cantidadTotalDeLabores(){
        Integer cantidadLabor = laborSueloService.obtenerCantidadLaborSuelo();
        return new ResponseEntity<>(cantidadLabor, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('PRODUCTOR')")
    @GetMapping("/detalle/{id}")
    ResponseEntity<LaborSuelo> obteberDetalleDeUnaLabor(@PathVariable("id") Long id){
        if(!laborSueloService.existsByIdLabor(id))
            return new ResponseEntity(new Mensaje("no existe esa Labor"),HttpStatus.NOT_FOUND);
        LaborSuelo laborSuelo = laborSueloService.getLaborSuelo(id).get();
        return new ResponseEntity(laborSuelo,HttpStatus.OK);
    }


}
