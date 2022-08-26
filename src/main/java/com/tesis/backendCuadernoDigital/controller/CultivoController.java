package com.tesis.backendCuadernoDigital.controller;

import com.tesis.backendCuadernoDigital.dto.CultivoDto;
import com.tesis.backendCuadernoDigital.dto.Mensaje;
import com.tesis.backendCuadernoDigital.entity.Cultivo;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import com.tesis.backendCuadernoDigital.security.service.UsuarioService;
import com.tesis.backendCuadernoDigital.service.CultivoService;
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
@RequestMapping("/cultivo")
@CrossOrigin("*")
public class CultivoController {


    @Autowired
    UsuarioService usuarioService;

    @Autowired
    CultivoService cultivoService;

    @Autowired
    LogService logService;

    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @PostMapping("/crearCultivo")
    public ResponseEntity<?> crearCultivo(@Valid @RequestBody CultivoDto cultivoDto, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal ingresados"), HttpStatus.BAD_REQUEST);

        if (cultivoService.existByNombre(cultivoDto.getNombre()))
            return new ResponseEntity(new Mensaje("Ese Cultivo ya existe"), HttpStatus.BAD_REQUEST);

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = usuarioService.getUsuarioLogeado(auth);
            Cultivo nuevoCultivo = new Cultivo(cultivoDto.getNombre(),cultivoDto.getRemito(),cultivoDto.getTimpoCarencia(),
                    cultivoDto.getVariedadCultivo(),cultivoDto.getViveroProvedor());
            boolean result = cultivoService.guardarCultivo(nuevoCultivo);
            if(result){
                logService.guardarAltaCultivo(nuevoCultivo,usuario);
                return new ResponseEntity<>(new Mensaje("Cultivo guardado correctamente"),HttpStatus.CREATED);
            }
            return new ResponseEntity(new Mensaje("Fallo la operacion, Cultivo no Registrado"), HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e){
            return new ResponseEntity(new Mensaje("Fallo la operacion, Cultivo no Registrado"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @GetMapping("/listado")
    public ResponseEntity<List<Cultivo>> listadoCultivo(){
        List<Cultivo> listado = cultivoService.listadoCultivoPorNombre();
        return new ResponseEntity<>(listado, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @PutMapping("/modificar/{id}")
    public ResponseEntity<?> modificarCultivo(@PathVariable ("id") Long id, @Valid @RequestBody CultivoDto cultivoDto, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal ingresados"), HttpStatus.BAD_REQUEST);
        if(!cultivoService.existsByIdCultivo(id))
            return new ResponseEntity(new Mensaje("no existe ese Cultivo"), HttpStatus.NOT_FOUND);
        if (StringUtils.isBlank(cultivoDto.getNombre()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = usuarioService.getUsuarioLogeado(auth);
            Cultivo cultivoModificado = cultivoService.getUnCultivo(id).get();
            cultivoModificado.setNombre(cultivoDto.getNombre());
            cultivoModificado.setRemito(cultivoDto.getRemito());
            cultivoModificado.setTimpoCarencia(cultivoDto.getTimpoCarencia());
            cultivoModificado.setVariedadCultivo(cultivoDto.getVariedadCultivo());
            cultivoModificado.setViveroProvedor(cultivoDto.getViveroProvedor());
            cultivoService.actualizarCultivo(cultivoModificado);
            if (cultivoModificado !=null){
                logService.modificarCultivo(cultivoModificado,usuario);
                return new ResponseEntity<>(new Mensaje("Cultivo actualizado correctamente"), HttpStatus.OK);
            }
            return new ResponseEntity(new Mensaje("Fallo la operacion, Cultivo no Modificado"), HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e){
            return new ResponseEntity(new Mensaje("Fallo la operacion, Cultivo no Modificado"), HttpStatus.INTERNAL_SERVER_ERROR);
        }



    }

}
