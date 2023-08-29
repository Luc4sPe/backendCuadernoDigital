package com.tesis.backendCuadernoDigital.controller;

import com.tesis.backendCuadernoDigital.dto.Mensaje;
import com.tesis.backendCuadernoDigital.dto.ModificacionPlantacionDto;
import com.tesis.backendCuadernoDigital.dto.PlantacionDto;
import com.tesis.backendCuadernoDigital.entity.Cuadro;
import com.tesis.backendCuadernoDigital.entity.Cultivo;
import com.tesis.backendCuadernoDigital.entity.Finca;
import com.tesis.backendCuadernoDigital.entity.Plantacion;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/plantacion")
@CrossOrigin("*")
public class PlantacionController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    LogService logService;

    @Autowired
    PlantacionService plantacionService;

    @Autowired
    CultivoService cultivoService;
    @Autowired
    CuadroService cuadroService;
    @Autowired
    FincaService fincaService;

    @PreAuthorize("hasAnyRole('ADMIN','PRODUCTOR')")
    @PostMapping("/crearPlantacion")
    public ResponseEntity<?> crearCultivo(@Valid @RequestBody PlantacionDto plantacionDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal ingresados"), HttpStatus.BAD_REQUEST);

        if(plantacionService.existsByIdPlantacion(plantacionDto.getIdPlantacion()))
            return new ResponseEntity(new Mensaje("Esa plantacion ya existe"), HttpStatus.BAD_REQUEST);

        if(plantacionDto.getEntreIleras()<0)
            return new ResponseEntity(new Mensaje("El espacio entre las Hileras tiene q ser positiva"), HttpStatus.NOT_ACCEPTABLE);

        if(plantacionDto.getEntrePlantas()<0)
            return new ResponseEntity(new Mensaje("El espacio entre las plantas tiene q ser positiva"), HttpStatus.NOT_ACCEPTABLE);

        if (StringUtils.isBlank(plantacionDto.getObservacion()))
            return new ResponseEntity(new Mensaje("La observacion es obligatoria"), HttpStatus.BAD_REQUEST);

        if(plantacionDto.getCantidadPlantines()<0)
            return new ResponseEntity(new Mensaje("La cantidad de plantines tiene q ser positiva"), HttpStatus.NOT_ACCEPTABLE);

        Optional<Cultivo> cultivoOptional = cultivoService.getUnCultivo(plantacionDto.getTipoCultivo());
        Cultivo nombreCultivo= cultivoOptional.get();

        Cuadro getIdCuadro= cuadroService.getCuadro(plantacionDto.getIdCuadro());

        try {
            Finca finca = fincaService.getFincas(plantacionDto.getIdFinca());
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = usuarioService.getUsuarioLogeado(auth);
            Plantacion nuevaPlantacion = new Plantacion(plantacionDto.getEntreIleras(),plantacionDto.getEntrePlantas(),getIdCuadro,plantacionDto.getObservacion(),
                    "",plantacionDto.getSistemaRiego(),plantacionDto.getSistemaTrasplante(),nombreCultivo,
                    plantacionDto.getCantidadPlantines(),finca);



            // recorre la lista para ir guardando cada plantacion en un cuadro de la lista

          /* List<Cuadro> cuadros = plantacionDto.getNumerosDeCuadros()
                   .stream()
                   .map(cuadro -> cuadroService.getCuadro(cuadro.getIdCuadro()))
                   .distinct()
                   .collect(Collectors.toList());
           nuevaPlantacion.setNumerosDeCuadros(cuadros);

           */

            //if (plantacionDto.getNumerosDeCuadros().contains(nuevaPlantacion))
            if(plantacionDto.getIdCuadro().equals(nuevaPlantacion.getCuadro().getIdCuadro()))
                return new ResponseEntity(new Mensaje("Ya existe una plantación en este cuadro"), HttpStatus.BAD_REQUEST);

            boolean result = plantacionService.guardarPlantacion(nuevaPlantacion);
            nuevaPlantacion.setFechaCosecha(nuevaPlantacion.calculoFechaCosecha(nuevaPlantacion.getFechaCreacionPlantacion(),nombreCultivo.getTiempoDeCultivo()));
            if(result) {
                logService.guardarPlantacion(nuevaPlantacion, usuario);
                return new ResponseEntity<>(new Mensaje("La plantación se guardado correctamente"), HttpStatus.CREATED);
            }
            return new ResponseEntity(new Mensaje("Fallo la operacion, Plantacion no Registrada"), HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e){
            return new ResponseEntity(new Mensaje("Fallo la operacion, Plantacion no Registrada"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','PRODUCTOR')")
    @GetMapping("/listadoPlantacion")
    public ResponseEntity<List<Plantacion>> listadoPlantacion(){
        List<Plantacion> listado = plantacionService.ListarPlantacion();
        return new ResponseEntity<>(listado, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','PRODUCTOR')")
    @PutMapping("/modificar/{id}")
    public ResponseEntity<?> modificarPlantacion(@PathVariable ("id") Long id, @Valid @RequestBody ModificacionPlantacionDto modificacionPlantacionDto, BindingResult bindingResult){

        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal ingresados"), HttpStatus.BAD_REQUEST);

        if(!plantacionService.existsByIdPlantacion(id))
            return new ResponseEntity(new Mensaje("no existe esa Plantación"), HttpStatus.NOT_FOUND);

        if(modificacionPlantacionDto.getEntreIleras()<0)
            return new ResponseEntity(new Mensaje("El espacio entre las Hileras tiene q ser positiva"), HttpStatus.NOT_ACCEPTABLE);

        if(modificacionPlantacionDto.getEntrePlantas()<0)
            return new ResponseEntity(new Mensaje("El espacio entre las plantas tiene q ser positiva"), HttpStatus.NOT_ACCEPTABLE);

        //if (StringUtils.isBlank(modificacionPlantacionDto.getTipoCultivo()))
        //  return new ResponseEntity(new Mensaje("el nombre de Cultivo es obligatorio"), HttpStatus.BAD_REQUEST);

        if (StringUtils.isBlank(modificacionPlantacionDto.getJustificacion()))
            return new ResponseEntity(new Mensaje("Tiene que declarar una justificación"), HttpStatus.BAD_REQUEST);

        if(modificacionPlantacionDto.getCantidadPlantines()<0)
            return new ResponseEntity(new Mensaje("La cantidad de plantines tiene q ser positiva"), HttpStatus.NOT_ACCEPTABLE);

        Optional<Plantacion> plantacionOptional = plantacionService.getPlantacion(id);
        Plantacion justificacion = plantacionOptional.get();

        if (!justificacion.getJustificacion().isEmpty())
            return new ResponseEntity(new Mensaje("El archivo ya ha sido modificado anteriormente "), HttpStatus.BAD_REQUEST);
        

        try {

            Optional<Cultivo> cultivoOptional = cultivoService.getUnCultivo(modificacionPlantacionDto.getTipoCultivo());
            Cultivo nombreCultivo= cultivoOptional.get();
            Cuadro getIdCuadro= cuadroService.getCuadro(modificacionPlantacionDto.getIdCuadro());

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = usuarioService.getUsuarioLogeado(auth);
            /*
            Optional<Finca> fincaOptional = fincaService.getFinca(modificacionPlantacionDto.getIdFinca());
            Finca fincaCapturado = fincaOptional.get();


            List<Cuadro> cuadros = modificacionPlantacionDto.getNumerosDeCuadros()
                    .stream()
                    .map(cuadro -> cuadroService.getCuadro(cuadro.getIdCuadro()))
                    .collect(Collectors.toList());

             */

            Plantacion modificarPlantacion = plantacionService.getPlantacion(id).get();
            modificarPlantacion.setEntreIleras(modificacionPlantacionDto.getEntreIleras());
            modificarPlantacion.setEntrePlantas(modificacionPlantacionDto.getEntrePlantas());
            modificarPlantacion.setObservacion(modificacionPlantacionDto.getObservacion());
            modificarPlantacion.setJustificacion(modificacionPlantacionDto.getJustificacion());
            modificarPlantacion.setSistemaRiego(modificacionPlantacionDto.getSistemaRiego());
            modificarPlantacion.setSistemaTrasplante(modificacionPlantacionDto.getSistemaTrasplante());
            modificarPlantacion.setNombreTipoCultivo(nombreCultivo);
            modificarPlantacion.setCantidadPlantines(modificacionPlantacionDto.getCantidadPlantines());
            modificarPlantacion.setCuadro(getIdCuadro);
            plantacionService.actualizarPlantacion(modificarPlantacion);

            if(modificarPlantacion!=null) {
                logService.modificarPlantacion(modificarPlantacion, usuario);
                return new ResponseEntity<>(new Mensaje("La Plantacion se actualizo correctamente"), HttpStatus.OK);
            }
            return new ResponseEntity(new Mensaje("Fallo la operacion, Cultivo no Modificado"), HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e){
            return new ResponseEntity(new Mensaje("Fallo la operacion, plantación no modificadaaa"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','PRODUCTOR')")
    @GetMapping("/detallePlantacion/{id}")
    ResponseEntity<Plantacion> obteberDetalleDeUnaPlantacion(@PathVariable("id") Long id){
        if(!plantacionService.existsByIdPlantacion(id))
            return new ResponseEntity(new Mensaje("no existe esa plantacion"),HttpStatus.NOT_FOUND);
        Plantacion plantacion = plantacionService.getPlantacion(id).get();
        return new ResponseEntity(plantacion,HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('ADMIN','PRODUCTOR')")
    @GetMapping("/listadoPlantacionPorCultivo/{idCultivo}")
    public ResponseEntity<List<Plantacion>> listadoPlantacionPorCultivo(@PathVariable ("idCultivo") Long idCultivo){
        Cultivo cultivo = cultivoService.getCultivo(idCultivo);
        List<Plantacion> plantacion = plantacionService.listadoPlantacionDeUnCultivoPorId(cultivo.getIdCultivo());
        return new ResponseEntity(plantacion,HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('ADMIN','PRODUCTOR')")
    @GetMapping("/listadoPlantacionDeUnaFinca/{idFinca}")
    public ResponseEntity<List<Cuadro>> listadoPlantacionDeUnaFinca(@PathVariable ("idFinca") Long idFinca){
        Finca finca = fincaService.getFincas(idFinca);
        List<Plantacion> plantacion = plantacionService.getListadoPlantacionDeUnaFinca(finca.getIdFinca());
        return new ResponseEntity(plantacion,HttpStatus.OK);
    }

    /*
    @PreAuthorize("hasAnyRole('ADMIN','PRODUCTOR')")
    @GetMapping("/obtener/{idCuadro}")
    public ResponseEntity<Cuadro> obtenerPlantacionPorCuadro(@PathVariable ("idCuadro") Long idCuadro){
        Cuadro cuadro =cuadroService.getCuadro(idCuadro);
        Plantacion plantacion = plantacionService.getPlantacionPorCuadro(cuadro.getIdCuadro());
        return new ResponseEntity(plantacion,HttpStatus.OK);
    }

     */




}
