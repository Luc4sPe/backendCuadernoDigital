package com.tesis.backendCuadernoDigital.controller;

import com.tesis.backendCuadernoDigital.dto.AgroquimicoDto;
import com.tesis.backendCuadernoDigital.dto.EditarAgroquimico;
import com.tesis.backendCuadernoDigital.dto.Mensaje;
import com.tesis.backendCuadernoDigital.entity.Agroquimico;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import com.tesis.backendCuadernoDigital.security.service.UsuarioService;
import com.tesis.backendCuadernoDigital.service.AgroquimicoService;
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
@RequestMapping("/agroquimico")
@CrossOrigin("*")
public class AgroquimicoController {

    @Autowired
    AgroquimicoService agroquimicoService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    LogService logService;



    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @PostMapping("/nuevoAgroquimico")
    public ResponseEntity<?> crearAgroquimico(@Valid @RequestBody AgroquimicoDto agroquimicoDto, BindingResult bindingResult){

        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal ingresados"), HttpStatus.BAD_REQUEST);

        if(agroquimicoService.existsByNombreComercial(agroquimicoDto.getNombreComercial()))
            return new ResponseEntity(new Mensaje("Ese nombre comersial ya existe"), HttpStatus.BAD_REQUEST);

        if(agroquimicoDto.getTiempoDeCarencia()<0)
            return new ResponseEntity(new Mensaje("El timepo de carencia debe ser positiva"), HttpStatus.NOT_ACCEPTABLE);

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = usuarioService.getUsuarioLogeado(auth);

            Agroquimico agroquimico = new Agroquimico(agroquimicoDto.getNombreComercial(),agroquimicoDto.getFormulaYconcentracion(),agroquimicoDto.getPrincipioActivo(),
                    agroquimicoDto.getTipo(),agroquimicoDto.getTiempoDeCarencia(),agroquimicoDto.getDosisPorHectaria(),agroquimicoDto.getDosisPorHl(),agroquimicoDto.getVolumenPorHectaria(),
                    agroquimicoDto.getNumLote());

            boolean result = agroquimicoService.guardarAgroquimico(agroquimico);

            if(result){
                logService.guardarAgroquimico(agroquimico,usuario);
                return new ResponseEntity<>(new Mensaje("El agroquímico se guardado correctamente"),HttpStatus.CREATED);
            }
            return new ResponseEntity(new Mensaje("Fallo la operacion, agroquímico no registrado"), HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e){
            return new ResponseEntity(new Mensaje("Fallo la operacion, agroquímico no registrado"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasAnyRole('ADMIN','ENCARGADO_AGRICOLA', 'PRODUCTOR')")
    @GetMapping("/listarAgroquimico")
    public ResponseEntity<List<Agroquimico>> listadoAgroquimico(){
        List<Agroquimico> listado = agroquimicoService.listarAgroquimico();
        return new ResponseEntity<>(listado, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','ENCARGADO_AGRICOLA')")
    @GetMapping("/detalleAgro/{id}")
    ResponseEntity<Agroquimico> obteberDetalleDeUnAgroquimico(@PathVariable("id") Long id){
        if(!agroquimicoService.existsByIdAgro(id))
            return new ResponseEntity(new Mensaje("no existe ese agroquímico"),HttpStatus.NOT_FOUND);
        Agroquimico agroquimico = agroquimicoService.getUnAgroquimico(id).get();
        return new ResponseEntity(agroquimico,HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> editarAgroquimico(@PathVariable("id")Long id, @RequestBody EditarAgroquimico editarAgroquimico, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("Campos mal ingresado"), HttpStatus.BAD_REQUEST);

        if (!agroquimicoService.existsByIdAgro(id))
            return new ResponseEntity(new Mensaje("no existe ese de agroquímico"), HttpStatus.NOT_FOUND);

        //if(agroquimicoService.existsByNombreComercial(editarAgroquimico.getNombreComercial()))
          //  return new ResponseEntity(new Mensaje("Ese nombre comersial ya existe"), HttpStatus.BAD_REQUEST);

        if(editarAgroquimico.getTiempoDeCarencia()<0)
            return new ResponseEntity(new Mensaje("El timepo de carencia debe ser positiva"), HttpStatus.NOT_ACCEPTABLE);

        try {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = usuarioService.getUsuarioLogeado(auth);
            Agroquimico modificarAgroquimico = agroquimicoService.getUnAgroquimico(id).get();
            modificarAgroquimico.setNombreComercial(editarAgroquimico.getNombreComercial());
            modificarAgroquimico.setFormulaYconcentracion(editarAgroquimico.getFormulaYconcentracion());
            modificarAgroquimico.setPrincipioActivo(editarAgroquimico.getPrincipioActivo());
            modificarAgroquimico.setTipo(editarAgroquimico.getTipo());
            modificarAgroquimico.setTiempoDeCarencia(editarAgroquimico.getTiempoDeCarencia());
            modificarAgroquimico.setDosisPorHectaria(editarAgroquimico.getDosisPorHectaria());
            modificarAgroquimico.setDosisPorHl(editarAgroquimico.getDosisPorHl());
            modificarAgroquimico.setVolumenPorHectaria(editarAgroquimico.getVolumenPorHectaria());
            modificarAgroquimico.setNumLote((editarAgroquimico.getNumLote()));

            if (modificarAgroquimico !=null){
                logService.modificarAgroquimico(modificarAgroquimico,usuario);
                return new ResponseEntity<>(new Mensaje("Agroquímico actualizado correctamente"), HttpStatus.OK);
            }
            return new ResponseEntity(new Mensaje("Agroquímico actualizado"), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(new Mensaje("Fallo la operacion, agroquímico  no actualizado"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasAnyRole('ADMIN','ENCARGADO_AGRICOLA')")
    @GetMapping("/total")
    public ResponseEntity<?> cantidadTotalAgroquimicos(){
        Integer cantidad = agroquimicoService.getCantidadDeAgroquimicos();
        return new ResponseEntity(cantidad, HttpStatus.OK);
    }

    /*


    @DeleteMapping("borrar/{id}")
    public ResponseEntity<?> borrar(@PathVariable("id") long id){
        if(!agroquimicoService.existeById(id))
            return new ResponseEntity(new Mensaje("no existe ese AgroQuímico"),HttpStatus.NOT_FOUND);
        try {
            agroquimicoService.borrar(id);
            return new ResponseEntity(new Mensaje("AgroQuímico eliminada con exito"),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(new Mensaje("Fallo la operacion, AgroQuímico no eliminada"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

     */
}


