package com.tesis.backendCuadernoDigital.controller;


import com.tesis.backendCuadernoDigital.dto.AplicacionAgroquimicoDto;
import com.tesis.backendCuadernoDigital.dto.EditarAplicacionAgroquimicoDto;
import com.tesis.backendCuadernoDigital.dto.Mensaje;
import com.tesis.backendCuadernoDigital.entity.Agroquimico;
import com.tesis.backendCuadernoDigital.entity.AplicacionDeAgroquimico;
import com.tesis.backendCuadernoDigital.entity.Cuadro;
import com.tesis.backendCuadernoDigital.entity.Finca;

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
@RequestMapping("/aplicacionAgroquimico")
@CrossOrigin("*")
public class AplicacionAgroquimicoController {

    @Autowired
    AplicacionAgroquimicoService aplicacionAgroquimicoService;
    @Autowired
    FincaService fincaService;
    @Autowired
    AgroquimicoService agroquimicoService;

    @Autowired
    CuadroService cuadroService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    LogService logService;

    @PreAuthorize("hasAnyRole('ADMIN','PRODUCTOR')")
    @PostMapping("/nuevoApliAgroquimico")
    public ResponseEntity<?> crearAplicacionAgroquimico(@Valid @RequestBody AplicacionAgroquimicoDto aplicacionAgroquimicoDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal ingresados"), HttpStatus.BAD_REQUEST);

       // if (!aplicacionAgroquimicoService.existeById(aplicacionAgroquimicoDto.getId()))
         //   return new ResponseEntity(new Mensaje("Esa aplicacion de agroquimico ya existe"), HttpStatus.BAD_REQUEST);

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

       // if (aplicacionAgroquimicoDto.getIdFinca()<0)
         //   return new ResponseEntity(new Mensaje("El id de la finca no puede ser negativo"), HttpStatus.BAD_REQUEST);


        try {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = usuarioService.getUsuarioLogeado(auth);
            Finca finca = fincaService.getFincas(aplicacionAgroquimicoDto.getIdFinca());

            Cuadro getIdCuadro= cuadroService.getCuadro(aplicacionAgroquimicoDto.getIdCuadro());
            Agroquimico getIdAgroquimico=agroquimicoService.getAgroquimico(aplicacionAgroquimicoDto.getIdAgroquimico());

            AplicacionDeAgroquimico aplicacionDeAgroquimico = new AplicacionDeAgroquimico(getIdAgroquimico,getIdCuadro,aplicacionAgroquimicoDto.getDosisPorHectaria(),
                    aplicacionAgroquimicoDto.getDosisPorHl(),aplicacionAgroquimicoDto.getVolumenPorHectaria(),aplicacionAgroquimicoDto.getObjetivo(),aplicacionAgroquimicoDto.getObservaciones(),"",aplicacionAgroquimicoDto.getPlaga(),finca);


            //if( aplicacionDeAgroquimico.getPlantacion().getFechaCosecha().after(aplicacionDeAgroquimico.getFechaDeAplicacion()))
              //  return new ResponseEntity(new Mensaje("El tiempo de carencia es superior al permitido"), HttpStatus.BAD_REQUEST);

            boolean resultado = aplicacionAgroquimicoService.guardarAplicaAgroquimico(aplicacionDeAgroquimico);

            if(resultado){
                logService.guardarAplicacionAgroquimico(aplicacionDeAgroquimico,usuario);
                return new ResponseEntity<>(new Mensaje("La  aplicación de agroquímico se guardado correctamente"),HttpStatus.CREATED);
            }
            return new ResponseEntity(new Mensaje(" Fallo la operacion, la  aplicación de agroquímico no registrada"), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity(new Mensaje("Fallo la operacionnn, de aplicacion de Agroquímico no registrada"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasAnyRole('ADMIN','PRODUCTOR')")
    @GetMapping("/listadoAplicacionAgroDeUnaFinca/{idFinca}")
    public ResponseEntity<List<Cuadro>> listadoPlantacionDeUnaFinca(@PathVariable ("idFinca") Long idFinca){
        Finca finca = fincaService.getFincas(idFinca);
        List<AplicacionDeAgroquimico> aplicacion = aplicacionAgroquimicoService.getListadoAplicacionAgroDeUnaFincaPorId(finca.getIdFinca());
        return new ResponseEntity(aplicacion,HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','PRODUCTOR')")
    @GetMapping("/detalleAplicacionAgro/{id}")
    ResponseEntity<AplicacionDeAgroquimico> obteberDetalleDeUnaAplicacionAgro(@PathVariable("id") Long id){
        if(!aplicacionAgroquimicoService.existeById(id))
            return new ResponseEntity(new Mensaje("no existe esa aplicación de agroquímico"),HttpStatus.NOT_FOUND);
        AplicacionDeAgroquimico aplicacion = aplicacionAgroquimicoService.getUnAplicacionAgroquimico(id).get();
        return new ResponseEntity(aplicacion,HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','PRODUCTOR')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> editarApliAgroquimico(@PathVariable("id") Long id, @Valid @RequestBody EditarAplicacionAgroquimicoDto editarAplicacionAgroquimicoDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("Campos mal ingresado"), HttpStatus.BAD_REQUEST);

        if (!aplicacionAgroquimicoService.existeById(id))
            return new ResponseEntity(new Mensaje("no existe esa aplicación de agroquímico"), HttpStatus.NOT_FOUND);

       if(editarAplicacionAgroquimicoDto.getDosisPorHl()<0)
            return new ResponseEntity(new Mensaje("La dosis por Hl debe ser positiva"), HttpStatus.NOT_ACCEPTABLE);

        if(editarAplicacionAgroquimicoDto.getVolumenPorHectaria()<0)
            return new ResponseEntity(new Mensaje("El volumen por hectaria debe ser positiva"), HttpStatus.NOT_ACCEPTABLE);

        if (editarAplicacionAgroquimicoDto.getIdCuadro()<0)
            return new ResponseEntity(new Mensaje("El id del cuadro no puede ser negativo"), HttpStatus.BAD_REQUEST);

        if (StringUtils.isBlank(editarAplicacionAgroquimicoDto.getObservaciones()))
            return new ResponseEntity(new Mensaje("La observacion es obligatoria"), HttpStatus.BAD_REQUEST);

        //se obtiene una aplicaclion a traves del id para manupular uno de los atributo de la clase en este caso justificación
        AplicacionDeAgroquimico apliAgroquimico=aplicacionAgroquimicoService.getAplicaAgroquimico(id);

        if(!apliAgroquimico.getJustificacion().isEmpty())
            return new ResponseEntity(new Mensaje("El archivo ya ha sido modificado anteriormente "), HttpStatus.BAD_REQUEST);

        try {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = usuarioService.getUsuarioLogeado(auth);

            Cuadro getCuadro= cuadroService.getCuadro(editarAplicacionAgroquimicoDto.getIdCuadro());
            Agroquimico getAgroquimico=agroquimicoService.getAgroquimico(editarAplicacionAgroquimicoDto.getIdAgroquimico());

            AplicacionDeAgroquimico modificarAplicacionAgro = aplicacionAgroquimicoService.getUnAplicacionAgroquimico(id).get();
            modificarAplicacionAgro.setAgroquimico(getAgroquimico);
            modificarAplicacionAgro.setCuadro(getCuadro);
            modificarAplicacionAgro.setDosisPorHectaria(editarAplicacionAgroquimicoDto.getDosisPorHectaria());
            modificarAplicacionAgro.setDosisPorHl(editarAplicacionAgroquimicoDto.getDosisPorHl());
            modificarAplicacionAgro.setVolumenPorHectaria(editarAplicacionAgroquimicoDto.getVolumenPorHectaria());
            modificarAplicacionAgro.setObjetivo(editarAplicacionAgroquimicoDto.getObjetivo());
            modificarAplicacionAgro.setObservaciones(editarAplicacionAgroquimicoDto.getObservaciones());
            modificarAplicacionAgro.setJustificacion(editarAplicacionAgroquimicoDto.getJustificacion());
            modificarAplicacionAgro.setPlaga(editarAplicacionAgroquimicoDto.getPlaga());

            aplicacionAgroquimicoService.actualizarAplicaAgroquimico(modificarAplicacionAgro);

            if(modificarAplicacionAgro!=null){

                logService.modificarAplicacionAgroquimico(modificarAplicacionAgro,usuario);
                return new ResponseEntity<>(new Mensaje(" Aplicacion de agroquímico actualizado correctamente"), HttpStatus.OK);
            }
            return new ResponseEntity(new Mensaje("Aplicacion agroquimico actualizado"),HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity(new Mensaje("Fallo la operacion, aplicacion Agroquímico  no actualizado"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    /*

    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA','PRODUCTOR')")
    @GetMapping("/listarAplicacionAgroquimico")
    public ResponseEntity<List<AplicacionDeAgroquimico>> listaAplicacionAgroQuimico() {
        List<AplicacionDeAgroquimico> lista = aplicacionAgroquimicoService.listarAplicacionAgroquimico();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA','PRODUCTOR')")
    @GetMapping("/detalle/{id}")
    public ResponseEntity<AplicacionDeAgroquimico> getByPorId(@PathVariable("id") long id) {
        if (!aplicacionAgroquimicoService.existeById(id))
            return new ResponseEntity(new Mensaje("no existe esa aplicacion Agroquímicos"), HttpStatus.NOT_FOUND);
        try {
            AplicacionDeAgroquimico aplicacionDeAgroquimico = aplicacionAgroquimicoService.getUnoById(id).get();
            return new ResponseEntity(aplicacionDeAgroquimico, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new Mensaje("Fallo la operacion, aplicacion de agroquímico no optenido"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA',PRODUCTOR)")
    @GetMapping("/AplicacionAgroquiPorEncarAgriNombreUsuario/{nombreUsuario}")
    public ResponseEntity<List<AplicacionDeAgroquimico>> listadoAplicacionAgroQuiDeUnUsuarioAgri(@PathVariable ("nombreUsuario") String nombreUsuario){
        List<AplicacionDeAgroquimico> listado = aplicacionAgroquimicoService.listarAplicacionAgroQuiPorNombreUsuario(nombreUsuario);
        return new ResponseEntity<>(listado,HttpStatus.OK);
    }



    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA','PRODUCTOR')")
    @DeleteMapping("borrar/{id}")
    public ResponseEntity<?> borrar(@PathVariable("id") long id){
        if(!aplicacionAgroquimicoService.existeById(id))
            return new ResponseEntity(new Mensaje("no existe esa aplicación de AgroQuímico"),HttpStatus.NOT_FOUND);
        try {
            aplicacionAgroquimicoService.borrar(id);
            return new ResponseEntity(new Mensaje("Aplicación de AgroQuímico eliminada con exito"),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(new Mensaje("Fallo la operacion, aplicación AgroQuímico no eliminada"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

     */
}

