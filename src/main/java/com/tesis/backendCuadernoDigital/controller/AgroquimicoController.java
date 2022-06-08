package com.tesis.backendCuadernoDigital.controller;

import com.tesis.backendCuadernoDigital.dto.AgroquimicoDto;
import com.tesis.backendCuadernoDigital.dto.EditarAgroquimico;
import com.tesis.backendCuadernoDigital.dto.Mensaje;
import com.tesis.backendCuadernoDigital.entity.Agroquimico;
import com.tesis.backendCuadernoDigital.entity.Plaga;
import com.tesis.backendCuadernoDigital.entity.TipoAgroquimico;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import com.tesis.backendCuadernoDigital.security.service.UsuarioService;
import com.tesis.backendCuadernoDigital.service.AgroquimicoService;
import com.tesis.backendCuadernoDigital.service.PlagaService;
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
@RequestMapping("/agroquimico")
@CrossOrigin("*")
public class AgroquimicoController {

    @Autowired
    AgroquimicoService agroquimicoService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    PlagaService plagaService;

    @Autowired
    TipoAgroquimicoService tipoAgroquimicoService;

    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @PostMapping("/nuevoAgroquimico")
    public ResponseEntity<?> crearAgroquimico(@Valid @RequestBody AgroquimicoDto agroquimicoDto, BindingResult bindingResult){

        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal ingresados"), HttpStatus.BAD_REQUEST);

        if(agroquimicoService.existeByNombreComun(agroquimicoDto.getNombreComun()))
            return new ResponseEntity(new Mensaje("Esa Agroquímico ya existe"), HttpStatus.BAD_REQUEST);

        Optional<Plaga> plagaOptional = plagaService.getByNombre(agroquimicoDto.getNombrePlaga());
        if(!plagaService.existeByNombre(agroquimicoDto.getNombrePlaga()))
            return new ResponseEntity(new Mensaje("Ese nombre de Plaga no existe"), HttpStatus.NOT_FOUND);

        Plaga plaga= plagaOptional.get();

        Optional<TipoAgroquimico> tipoAgroquimicoOptional = tipoAgroquimicoService.getByNombre(agroquimicoDto.getNombreTipoAgroquimico());
        if(!tipoAgroquimicoService.existeByNombre(agroquimicoDto.getNombreTipoAgroquimico()))
            return new ResponseEntity(new Mensaje("Ese nombre de tipo de Agroquímico no existe"), HttpStatus.NOT_FOUND);

        TipoAgroquimico tipoAgroquimico = tipoAgroquimicoOptional.get();

        Optional<Usuario> usuarioOptional = usuarioService.getByNombreUsuario(agroquimicoDto.getNombreEncargadoAgricola());

        if (!usuarioService.existsByNombreUsuario(agroquimicoDto.getNombreEncargadoAgricola()))
            return new ResponseEntity(new Mensaje("Ese nombre de usuario no existe"), HttpStatus.NOT_FOUND);

        Usuario usuarioEncargadoAgricola = usuarioOptional.get();

        Agroquimico agroquimico = new Agroquimico(agroquimicoDto.getFormulaYconcentracion(),agroquimicoDto.getLote(),agroquimicoDto.getNombreComun(),
                agroquimicoDto.getObservaciones(),plaga,agroquimicoDto.getPrincipioActivo(),agroquimicoDto.getTiempoDeCarencia(),
                tipoAgroquimico,usuarioEncargadoAgricola);
        try {
            this.agroquimicoService.guardar(agroquimico);
            return new ResponseEntity(new Mensaje(" Agroquímico Registrado con Exito"), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(new Mensaje("Fallo la operacion, Agroquímico no registrada"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @GetMapping("/listarTipoAgroquimico")
    public ResponseEntity<List<Agroquimico>> listaAgroQuimico(){
        List<Agroquimico> lista = agroquimicoService.listarAgroquimico();
        return new ResponseEntity<>(lista,HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @GetMapping("/detalle/{id}")
    public ResponseEntity<TipoAgroquimico> getByNombreComun(@PathVariable("id") long id) {
        if (!agroquimicoService.existeById(id))
            return new ResponseEntity(new Mensaje("no existe ese Agroquímicos"), HttpStatus.NOT_FOUND);
        try {
            Agroquimico agroquimico = agroquimicoService.getUnoId(id).get();
            return new ResponseEntity(agroquimico, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new Mensaje("Fallo la operacion, agroquímico no optenido"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @GetMapping("/agroquiPorEncarAgriNombreUsuario/{nombreUsuario}")
    public ResponseEntity<List<Agroquimico>> listadoTipoAgroQuiDeUnUsuarioAgri(@PathVariable ("nombreUsuario") String nombreUsuario){
        List<Agroquimico> listado = agroquimicoService.listarAgroquimicoPorNombreUsuario(nombreUsuario);
        return new ResponseEntity<>(listado,HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @PutMapping("/update/{id}")

    public ResponseEntity<?> editarAgroquimico(@PathVariable("id")long id, @RequestBody EditarAgroquimico editarAgroquimico, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("Campos mal ingresado"), HttpStatus.BAD_REQUEST);

        if (!agroquimicoService.existeById(id))
            return new ResponseEntity(new Mensaje("no existe ese de agroquímico"), HttpStatus.NOT_FOUND);

        if (agroquimicoService.existeByNombreComun(editarAgroquimico.getNombreComun()) && agroquimicoService.getByNombreComun(editarAgroquimico.getNombreComun()).get().getId() == id)
            return new ResponseEntity(new Mensaje("ese nombre de AgroQuímico ya existe"), HttpStatus.BAD_REQUEST);

        if (StringUtils.isBlank(editarAgroquimico.getNombreComun()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);

        Optional<TipoAgroquimico> tipoAgroquimicoOptional = tipoAgroquimicoService.getByNombre(editarAgroquimico.getNombreTipoAgroquimico());
        TipoAgroquimico nombreTipoAgro= tipoAgroquimicoOptional.get();

        Optional<Plaga> plagaOptional = plagaService.getByNombre(editarAgroquimico.getNombrePlaga());
        Plaga nombrePlaga = plagaOptional.get();

        Agroquimico modificarAgroquimico = agroquimicoService.getUnoId(id).get();
        modificarAgroquimico.setFormulaYconcentracion(editarAgroquimico.getFormulaYconcentracion());
        modificarAgroquimico.setLote(editarAgroquimico.getLote());
        modificarAgroquimico.setNombreComun(editarAgroquimico.getNombreComun());
        modificarAgroquimico.setObservaciones(editarAgroquimico.getObservaciones());
        modificarAgroquimico.setPlaga(nombrePlaga);
        modificarAgroquimico.setPrincipioActivo(editarAgroquimico.getPrincipioActivo());
        modificarAgroquimico.setTiempoDeCarencia(editarAgroquimico.getTiempoDeCarencia());
        modificarAgroquimico.setTipoAgroquimico(nombreTipoAgro);

        try {
            this.agroquimicoService.guardar(modificarAgroquimico);
            return new ResponseEntity(new Mensaje("TipoAgroquímico actualizado"), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(new Mensaje("Fallo la operacion, Agroquímico  no actualizado"), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

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
}
