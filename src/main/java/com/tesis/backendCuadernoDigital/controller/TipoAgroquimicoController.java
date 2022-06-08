package com.tesis.backendCuadernoDigital.controller;

import com.tesis.backendCuadernoDigital.dto.EditarTipoAgroQuimicoDto;
import com.tesis.backendCuadernoDigital.dto.Mensaje;
import com.tesis.backendCuadernoDigital.dto.TipoAgroquimicoDto;
import com.tesis.backendCuadernoDigital.entity.TipoAgroquimico;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import com.tesis.backendCuadernoDigital.security.service.UsuarioService;
import com.tesis.backendCuadernoDigital.service.TipoAgroquimicoService;
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
@RequestMapping("/tipoAgro")
@CrossOrigin("*")
public class TipoAgroquimicoController {

    @Autowired
    TipoAgroquimicoService tipoAgroquimicoService;

    @Autowired
    UsuarioService usuarioService;

    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @PostMapping("/nuevoTipoAgro")
    public ResponseEntity<?> crearTipoAgroQuimico(@Valid @RequestBody TipoAgroquimicoDto tipoAgroquimicoDto, BindingResult bindingResult){

        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal ingresados"), HttpStatus.BAD_REQUEST);

        if(tipoAgroquimicoService.existeByNombre(tipoAgroquimicoDto.getNombre()))
            return new ResponseEntity(new Mensaje("Ese tipo de agroquímico ya existe"), HttpStatus.BAD_REQUEST);

        Optional<Usuario> usuarioOptional = usuarioService.getByNombreUsuario(tipoAgroquimicoDto.getNombreEncargadoAgricola());

        if (!usuarioService.existsByNombreUsuario(tipoAgroquimicoDto.getNombreEncargadoAgricola()))
            return new ResponseEntity(new Mensaje("Ese nombre de usuario no existe"), HttpStatus.BAD_REQUEST);

        Usuario usuarioEncargadoAgricola = usuarioOptional.get();

        TipoAgroquimico nuevoTipoAgroQui = new TipoAgroquimico(tipoAgroquimicoDto.getNombre(),tipoAgroquimicoDto.getDescripcion(), usuarioEncargadoAgricola);

        try {
            this.tipoAgroquimicoService.save(nuevoTipoAgroQui);
            return new ResponseEntity(new Mensaje(" TipoAgroquímico Registrado con Exito"), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(new Mensaje("Fallo la operacion, TipoAgroquímico no registrada"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @GetMapping("/listarTipoAgroQui")
    public ResponseEntity<List<TipoAgroquimico>> listaTipoAgroQuimico(){
        List<TipoAgroquimico> lista = tipoAgroquimicoService.listarTipoAgroquimico();
        return new ResponseEntity<>(lista,HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @GetMapping("/detalle/{id}")
    public ResponseEntity<TipoAgroquimico> getByNombre(@PathVariable("id") long id) {
        if (!tipoAgroquimicoService.existeById(id))
            return new ResponseEntity(new Mensaje("no existe ese tipo de Agroquímicos"), HttpStatus.NOT_FOUND);
        try {
            TipoAgroquimico tipoAgro = tipoAgroquimicoService.getUnoById(id).get();
            return new ResponseEntity(tipoAgro, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new Mensaje("Fallo la operacion, tipo de agroquímico no optenido"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @GetMapping("/plagaPorEncarAgriNombreUsuario/{nombreUsuario}")
    public ResponseEntity<List<TipoAgroquimico>> listadoTipoAgroQuiDeUnUsuarioAgri(@PathVariable ("nombreUsuario") String nombreUsuario){
        List<TipoAgroquimico> listado = tipoAgroquimicoService.listarTipoAgroQuimicoPorNombreUsuario(nombreUsuario);
        return new ResponseEntity<>(listado,HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> editarTipoAgroQui(@PathVariable("id")long id, @RequestBody EditarTipoAgroQuimicoDto editarTipoAgroQuim, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("Campos mal ingresado"), HttpStatus.BAD_REQUEST);

        if(!tipoAgroquimicoService.existeById(id))
            return new ResponseEntity(new Mensaje("no existe ese tipo de agroquímico"), HttpStatus.NOT_FOUND);

        if(tipoAgroquimicoService.existeByNombre(editarTipoAgroQuim.getNombre()) && tipoAgroquimicoService.getByNombre(editarTipoAgroQuim.getNombre()).get().getId() ==id)
            return new ResponseEntity(new Mensaje("ese nombre de AgroQuímico ya existe"), HttpStatus.BAD_REQUEST);

        if (StringUtils.isBlank(editarTipoAgroQuim.getNombre()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);

        TipoAgroquimico modificarTipoAgroQui = tipoAgroquimicoService.getUnoById(id).get();

        modificarTipoAgroQui.setNombre(editarTipoAgroQuim.getNombre());
        modificarTipoAgroQui.setDescripcion(editarTipoAgroQuim.getDescripcion());

        try {
            tipoAgroquimicoService.save(modificarTipoAgroQui);
            return new ResponseEntity(new Mensaje("TipoAgroquímico actualizado"), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(new Mensaje("Fallo la operacion, TipoAgroquímico  no actualizado"), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @DeleteMapping("borrar/{id}")
    public ResponseEntity<?> borrar(@PathVariable("id") long id){
        if(!tipoAgroquimicoService.existeById(id))
            return new ResponseEntity(new Mensaje("no existe ese TipoAgroQuímico"),HttpStatus.NOT_FOUND);
        try {
            tipoAgroquimicoService.borrar(id);
            return new ResponseEntity(new Mensaje("TipoAgroQuímico eliminada con exito"),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(new Mensaje("Fallo la operacion, TipoAgroQuímico no eliminada"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
