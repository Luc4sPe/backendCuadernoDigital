package com.tesis.backendCuadernoDigital.controller;

/*
import com.tesis.backendCuadernoDigital.dto.AplicacionAgroquimicoDto;
import com.tesis.backendCuadernoDigital.dto.EditarAplicacionAgroquimicoDto;
import com.tesis.backendCuadernoDigital.dto.Mensaje;
import com.tesis.backendCuadernoDigital.entity.Agroquimico;
import com.tesis.backendCuadernoDigital.entity.AplicacionDeAgroquimico;
import com.tesis.backendCuadernoDigital.entity.Plaga;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import com.tesis.backendCuadernoDigital.security.service.UsuarioService;
import com.tesis.backendCuadernoDigital.service.AgroquimicoService;
import com.tesis.backendCuadernoDigital.service.AplicacionAgroquimicoService;
import com.tesis.backendCuadernoDigital.service.PlagaService;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/aolicacionAgroquimico")
@CrossOrigin("*")
public class AplicacionAgroquimicoController {

    @Autowired
    AplicacionAgroquimicoService aplicacionAgroquimicoService;
    @Autowired
    PlagaService plagaService;
    @Autowired
    AgroquimicoService agroquimicoService;

    @Autowired
    UsuarioService usuarioService;

    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA','PRODUCTOR')")
    @PostMapping("/nuevoApliAgroquimico")
    public ResponseEntity<?> crearAplicacionAgroquimico(@Valid @RequestBody AplicacionAgroquimicoDto aplicacionAgroquimicoDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal ingresados"), HttpStatus.BAD_REQUEST);

        if (aplicacionAgroquimicoService.existeById(aplicacionAgroquimicoDto.getId()))
            return new ResponseEntity(new Mensaje("Esa aplicacion de agroquimico ya existe"), HttpStatus.BAD_REQUEST);

        Optional<Agroquimico> agroquimicoOptional = agroquimicoService.getByNombreComun(aplicacionAgroquimicoDto.getNombreComunAgroquimico());
        if (!agroquimicoService.existeByNombreComun(aplicacionAgroquimicoDto.getNombreComunAgroquimico()))
            return new ResponseEntity(new Mensaje("Ese nombre de Agroquimico no existe"), HttpStatus.NOT_FOUND);

        Agroquimico nombreAgroquimico = agroquimicoOptional.get();

        Optional<Plaga> plagaOptional = plagaService.getByNombre(aplicacionAgroquimicoDto.getNombrePlaga());
        if (!plagaService.existeByNombre(aplicacionAgroquimicoDto.getNombrePlaga()))
            return new ResponseEntity(new Mensaje("Ese nombre de Plaga no existe"), HttpStatus.NOT_FOUND);

        Plaga plaga = plagaOptional.get();

        Optional<Usuario> usuarioOptional = usuarioService.getByNombreUsuario(aplicacionAgroquimicoDto.getNombreEncargadoAgricola());

        if (!usuarioService.existsByNombreUsuario(aplicacionAgroquimicoDto.getNombreEncargadoAgricola()))
            return new ResponseEntity(new Mensaje("Ese nombre de usuario no existe"), HttpStatus.NOT_FOUND);

        Usuario usuarioEncargadoAgricola = usuarioOptional.get();

        AplicacionDeAgroquimico aplicacionDeAgroquimico = new AplicacionDeAgroquimico(nombreAgroquimico, aplicacionAgroquimicoDto.getAplicacion(), aplicacionAgroquimicoDto.getNumeroCuadro(),
                aplicacionAgroquimicoDto.getObservaciones(), plaga, usuarioEncargadoAgricola);

        try {
            this.aplicacionAgroquimicoService.guardar(aplicacionDeAgroquimico);
            return new ResponseEntity(new Mensaje(" aplicacion de Agroquímico Registrada con Exito"), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(new Mensaje("Fallo la operacion, de aplicacion de Agroquímico no registrada"), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

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
    @PutMapping("/update/{id}")
    public ResponseEntity<?> editarApliAgroquimico(@PathVariable("id") long id, @RequestBody EditarAplicacionAgroquimicoDto editarAplicacionAgroquimicoDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("Campos mal ingresado"), HttpStatus.BAD_REQUEST);
        if (!aplicacionAgroquimicoService.existeById(id))
            return new ResponseEntity(new Mensaje("no existe esa aplicación de agroquímico"), HttpStatus.NOT_FOUND);
        if (StringUtils.isBlank(editarAplicacionAgroquimicoDto.getNombreComunAgroquimico()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(editarAplicacionAgroquimicoDto.getNombrePlaga()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        Optional<Plaga> plagaOptional = plagaService.getByNombre(editarAplicacionAgroquimicoDto.getNombrePlaga());
        Plaga nombrePlaga = plagaOptional.get();

        Optional<Agroquimico> agroquimicoOptional = agroquimicoService.getByNombreComun(editarAplicacionAgroquimicoDto.getNombreComunAgroquimico());
        Agroquimico nombreAgroquimico = agroquimicoOptional.get();

        AplicacionDeAgroquimico editarAplicacionAgro = aplicacionAgroquimicoService.getUnoById(id).get();

        editarAplicacionAgro.setAgroquimico(nombreAgroquimico);
        editarAplicacionAgro.setAplicacion(editarAplicacionAgroquimicoDto.getAplicacion());
        editarAplicacionAgro.setNumeroCuadro(editarAplicacionAgroquimicoDto.getNumeroCuadro());
        editarAplicacionAgro.setObservaciones(editarAplicacionAgroquimicoDto.getObservaciones());
        editarAplicacionAgro.setPlaga(nombrePlaga);

        try {
            this.aplicacionAgroquimicoService.guardar(editarAplicacionAgro);
            return new ResponseEntity(new Mensaje("aplicacion agroquimico actualizado"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new Mensaje("Fallo la operacion, aplicacion Agroquímico  no actualizado"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

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
}

 */