package com.tesis.backendCuadernoDigital.controller;

import com.tesis.backendCuadernoDigital.dto.EditarPlaga;
import com.tesis.backendCuadernoDigital.dto.Mensaje;
import com.tesis.backendCuadernoDigital.dto.PlagaDto;
import com.tesis.backendCuadernoDigital.entity.Plaga;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import com.tesis.backendCuadernoDigital.security.service.UsuarioService;
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
@RequestMapping("/plaga")
@CrossOrigin("*")
public class PlagaController {

    @Autowired
    PlagaService plagaService;
    @Autowired
    UsuarioService usuarioService;

    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @PostMapping("/nuevoPlaga")

    public ResponseEntity<?> crearPlaga(@Valid @RequestBody PlagaDto plagaDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal ingresados"), HttpStatus.BAD_REQUEST);

        if (plagaService.existeByNombre(plagaDto.getNombre()))
            return new ResponseEntity(new Mensaje("Esa Plaga ya existe"), HttpStatus.BAD_REQUEST);

        Optional<Usuario> usuarioOptional = usuarioService.getByNombreUsuario(plagaDto.getNombreEncargadoAgricola());

        if (!usuarioService.existsByNombreUsuario(plagaDto.getNombreEncargadoAgricola()))
            return new ResponseEntity(new Mensaje("Ese nombre de usuario no existe"), HttpStatus.BAD_REQUEST);

        Usuario usuarioEncargadoAgricola = usuarioOptional.get();

        Plaga nuevaPlaga = new Plaga(plagaDto.getNombre(), plagaDto.getDescripcion(), usuarioEncargadoAgricola);

        try {
            this.plagaService.save(nuevaPlaga);
            return new ResponseEntity(new Mensaje(" Plaga Registrada con Exito"), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(new Mensaje("Fallo la operacion, Plaga no registrada"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @GetMapping("/listarPlagas")
    public ResponseEntity<List<Plaga>> listadePlagas(){
        List<Plaga> lista = plagaService.listarPlaga();
        return new ResponseEntity<>(lista,HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @GetMapping("/detalle/{id}")
    public ResponseEntity<Plaga> getByNombre(@PathVariable("id") long id){
        if(!plagaService.existeById(id))
            return new ResponseEntity(new Mensaje("no existe esa plaga"),HttpStatus.NOT_FOUND);
        try {
            Plaga plaga = plagaService.getUnoByID(id).get();
            return new ResponseEntity(plaga,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(new Mensaje("Fallo la operacion, Plaga no optenida"), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @GetMapping("/plagaPorEncarAgriNombreUsuario/{nombreUsuario}")
    public ResponseEntity<List<Plaga>> listadoPlagaDeUnUsuarioAgri(@PathVariable ("nombreUsuario") String nombreUsuario){
        List<Plaga> listado = plagaService.listarPlagaPorNombreDeUsuario(nombreUsuario);
        return new ResponseEntity<>(listado,HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> editar(@PathVariable("id")long id, @RequestBody EditarPlaga editarPlaga, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("Campos mal ingresado"), HttpStatus.BAD_REQUEST);

        if(!plagaService.existeById(id))
            return new ResponseEntity(new Mensaje("no existe esa Plaga"), HttpStatus.NOT_FOUND);

        if(plagaService.existeByNombre(editarPlaga.getNombre()))
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);

        if (StringUtils.isBlank(editarPlaga.getNombre()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);

        Plaga actualizarPlaga = plagaService.getUnoByID(id).get();
      //  Optional<Plaga> plagaOptional = plagaService.getByNombre(editarPlaga.getNombre());
        //Plaga plaga = plagaOptional.get();

        actualizarPlaga.setNombre(editarPlaga.getNombre());
        actualizarPlaga.setDescripcion(editarPlaga.getDescripcion());

        try {
            plagaService.save(actualizarPlaga);
            return new ResponseEntity(new Mensaje("Plaga actualizada"), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(new Mensaje("Fallo la operacion, Plaga no actualizado"), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @DeleteMapping("borrar/{id}")
    public ResponseEntity<?> borrar(@PathVariable("id") long id){
        if(!plagaService.existeById(id))
            return new ResponseEntity(new Mensaje("no existe esa la Plaga"),HttpStatus.NOT_FOUND);
        try {
            plagaService.borrar(id);
            return new ResponseEntity(new Mensaje("Plaga eliminada con exito"),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(new Mensaje("Fallo la operacion, Plaga no eliminada"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
