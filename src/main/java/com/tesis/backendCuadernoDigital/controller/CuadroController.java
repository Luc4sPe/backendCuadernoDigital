package com.tesis.backendCuadernoDigital.controller;

import com.tesis.backendCuadernoDigital.dto.CuadroDto;
import com.tesis.backendCuadernoDigital.dto.Mensaje;
import com.tesis.backendCuadernoDigital.entity.Cuadro;
import com.tesis.backendCuadernoDigital.entity.Log;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import com.tesis.backendCuadernoDigital.security.service.UsuarioService;
import com.tesis.backendCuadernoDigital.service.CuadroService;
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

@RestController
@RequestMapping("/cuadro")
@CrossOrigin("*")
public class CuadroController {

    @Autowired
    CuadroService cuadroService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    LogService logService;

    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @PostMapping("/crearcuadro")
    public ResponseEntity<?> crearCultivo(@Valid @RequestBody CuadroDto cuadroDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal ingresados"), HttpStatus.BAD_REQUEST);
        if (cuadroService.existsByNumeroCuadro(cuadroDto.getNumeroCuadro()))
            return new ResponseEntity(new Mensaje("Ese Cuadro ya existe"), HttpStatus.BAD_REQUEST);

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = usuarioService.getUsuarioLogeado(auth);
            Cuadro nuevoCuadro = new Cuadro(cuadroDto.getNumeroCuadro(),cuadroDto.getSuperficieHectarea());
            boolean result = cuadroService.guardarCuadro(nuevoCuadro);
            if(result) {
                logService.guardarCuadro(nuevoCuadro, usuario);
                return new ResponseEntity<>(new Mensaje("El cuadro se guardado correctamente"), HttpStatus.CREATED);
            }
            return new ResponseEntity(new Mensaje("Fallo la operacion, Cuadro no Registrado"), HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e){
            return new ResponseEntity(new Mensaje("Fallo la operacion, Cuadro no Registrado"), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @GetMapping("/listadoCuadro")
    public ResponseEntity<List<Cuadro>> listadoCuadro() {
        List<Cuadro> listado = cuadroService.listarCuadros();
        return new ResponseEntity<>(listado, HttpStatus.OK);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @PutMapping("/modificarCuadro/{id}")
    public ResponseEntity<?> modificarFinca(@PathVariable ("id") Long id, @Valid @RequestBody CuadroDto cuadroDto, BindingResult bindingResult){

        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal ingresados"), HttpStatus.BAD_REQUEST);

        if (cuadroService.existsByIDCuadro(id))
            return new ResponseEntity(new Mensaje("no existe ese Cuadro"), HttpStatus.NOT_FOUND);

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = usuarioService.getUsuarioLogeado(auth);
            Cuadro modificarCuadro = cuadroService.getCuadro(id).get();
            modificarCuadro.setNumeroCuadro(cuadroDto.getNumeroCuadro());
            modificarCuadro.setSuperficieHectarea(cuadroDto.getSuperficieHectarea());
            modificarCuadro.setCultivoAnterior(cuadroDto.getCultivoAnterior());
            cuadroService.actualizarCuadro(modificarCuadro);
            if(modificarCuadro!=null) {
                logService.modificarCuadro(modificarCuadro, usuario);
                return new ResponseEntity<>(new Mensaje(" Finca actualizada correctamente"), HttpStatus.OK);
            }
            return new ResponseEntity(new Mensaje("Fallo la operacion, Cuadro no Actualizado"), HttpStatus.INTERNAL_SERVER_ERROR);

        }catch (Exception e){
            return new ResponseEntity(new Mensaje("Fallo la operacion, Cuadro no Registrado"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @GetMapping("/CantidadCuadro")
    public ResponseEntity<Integer> cantidadTotalDeCuadros(){
        Integer cantidad =cuadroService.getCantidadDeCuadros();
        return new ResponseEntity<>(cantidad, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @GetMapping("/detalle/{id}")
    ResponseEntity<Cuadro> obteberDetalleDeUnCuadro(@PathVariable("id") Long id){
        if(!cuadroService.existsByIDCuadro(id))
            return new ResponseEntity(new Mensaje("no existe ese Cuadro"),HttpStatus.NOT_FOUND);
        Cuadro cuadro = cuadroService.getCuadro(id).get();
        return new ResponseEntity(cuadro,HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @PutMapping("/agregarCultivoAnterior/{id}")
    public ResponseEntity<?> agregarCultivoAnterior(@PathVariable ("id") Long id, @Valid @RequestBody CuadroDto cuadroDto, BindingResult bindingResult){

        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal ingresados"), HttpStatus.BAD_REQUEST);

        if (!cuadroService.existsByIDCuadro(id))
            return new ResponseEntity(new Mensaje("no existe ese Cuadro"), HttpStatus.NOT_FOUND);

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = usuarioService.getUsuarioLogeado(auth);
            Cuadro agregarCualtivoAnterior = cuadroService.getCuadro(id).get();
            agregarCualtivoAnterior.setCultivoAnterior(cuadroDto.getCultivoAnterior());
            cuadroService.actualizarCuadro(agregarCualtivoAnterior);
            if(agregarCualtivoAnterior!=null) {
                logService.agregarCultivoAnterior(agregarCualtivoAnterior,usuario);
                return new ResponseEntity<>(new Mensaje(" Cultivo Anterior agregado con exito"), HttpStatus.OK);
            }
            return new ResponseEntity(new Mensaje("Fallo la operacion, Cultivo Anterior no agregado"), HttpStatus.INTERNAL_SERVER_ERROR);

        }catch (Exception e){
            return new ResponseEntity(new Mensaje("Fallo la operacion, Cultivo Anterior no agregado"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
