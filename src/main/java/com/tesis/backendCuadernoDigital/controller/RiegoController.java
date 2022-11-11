package com.tesis.backendCuadernoDigital.controller;
/*

import com.tesis.backendCuadernoDigital.dto.EditarRiegoDto;
import com.tesis.backendCuadernoDigital.dto.Mensaje;
import com.tesis.backendCuadernoDigital.dto.RiegoDto;
import com.tesis.backendCuadernoDigital.entity.Riego;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import com.tesis.backendCuadernoDigital.security.service.UsuarioService;
import com.tesis.backendCuadernoDigital.service.RiegoService;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/riego")
@CrossOrigin("*")
public class RiegoController {

    @Autowired
    RiegoService riegoService;

    @Autowired
    UsuarioService usuarioService;


    @PreAuthorize("hasAnyRole('ADMIN', 'PRODUCTOR')")
    @PostMapping("/nuevoRiego")
    public ResponseEntity<?> crearRiego(@Valid @RequestBody RiegoDto riegoDto, BindingResult bindingResult ){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal ingresados"), HttpStatus.BAD_REQUEST);

        if(riegoService.existsById(riegoDto.getId()))
            return new ResponseEntity(new Mensaje("Ese Riego ya existe"), HttpStatus.BAD_REQUEST);

       // if (riegoService.existsByNumeroCuadro(riegoDto.getNumeroDeCuadro()))
         //   return new ResponseEntity(new Mensaje("Ese cuadro ya se rego"), HttpStatus.BAD_REQUEST);

        Optional<Usuario> usuarioOptional = usuarioService.getByNombreUsuario(riegoDto.getNombreUsuario());

        if(!usuarioService.existsByNombreUsuario(riegoDto.getNombreUsuario()))
            return new ResponseEntity(new Mensaje("El Usuario Productor No existe"),HttpStatus.NOT_FOUND);

         Usuario usuario= usuarioOptional.get();

        Riego nuevoRiego = new Riego(riegoDto.getDuracionEnHoras(),riegoDto.getMilimetrosAplicados(),riegoDto.getNumeroDeCuadro()
        ,"",riegoDto.getObservacionProductor(),riegoDto.getSemanaAplicada(),riegoDto.getSemanaTransplante(),usuario);

        try {
            riegoService.save(nuevoRiego);
            return new ResponseEntity(new Mensaje(" Riego Registrado con Exito"), HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity(new Mensaje("Fallo la operacion, Riego no registrado"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





    //@Secured({"ROLE_ADMIN","ROLE_PRODUCTOR"})
    @PreAuthorize("hasAnyRole('ADMIN', 'PRODUCTOR','ENCARGADO_AGRICOLA')")
    @GetMapping("/lista")
    public ResponseEntity<List<Riego>> lista(){
        List<Riego> lista = riegoService.listaRiego();
        return new ResponseEntity<>(lista,HttpStatus.OK);
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<Riego> getByNombre(@PathVariable("id") int id){
        if(!riegoService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe el riego"),HttpStatus.NOT_FOUND);

        Riego riego = riegoService.getUnoById(id).get();
        return new ResponseEntity(riego,HttpStatus.OK);
    }




    @PreAuthorize("hasAnyRole('ADMIN', 'PRODUCTOR')")
    @GetMapping("/riegoPorUsuario/{idUsuario}")
    public ResponseEntity<List<Riego>> listadoRiegoDeUnUsuarioId(@PathVariable ("idUsuario") int idUsuario){
        List<Riego> listado = riegoService.listadoRiegoDeUnUsuario(idUsuario);
        return new ResponseEntity<>(listado,HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'PRODUCTOR')")
    @GetMapping("/riegoPorNombreUsuario/{nombreUsuario}")
    public ResponseEntity<List<Riego>> listadoRiegoDeUnUsuario(@PathVariable ("nombreUsuario") String nombreUsuario){
        List<Riego> listado = riegoService.listadoRiegoDeUnUsuarioPorNombre(nombreUsuario);
        return new ResponseEntity<>(listado,HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'PRODUCTOR')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id")int id, @RequestBody EditarRiegoDto editarRiego, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("Campos mal ingresado"), HttpStatus.BAD_REQUEST);

        if(!riegoService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe ese Riego"), HttpStatus.NOT_FOUND);

        if(!usuarioService.existsByNombreUsuario(editarRiego.getNombreUsuario()))
            return new ResponseEntity(new Mensaje("El Usuario Productor No existe"),HttpStatus.NOT_FOUND);

        if (StringUtils.isBlank(editarRiego.getNombreUsuario()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);

        Riego riegoActualizar = riegoService.getUnoById(id).get();
        Optional<Usuario> usuarioOptional = usuarioService.getByNombreUsuario(editarRiego.getNombreUsuario());
        Usuario usuario= usuarioOptional.get();

        riegoActualizar.setDuracionEnHoras(editarRiego.getDuracionEnHoras());
        riegoActualizar.setMilimetrosAplicados(editarRiego.getMilimetrosAplicados());
        riegoActualizar.setNumeroDeCuadro(editarRiego.getNumeroDeCuadro());
        riegoActualizar.setObservacionAsesor(editarRiego.getObservacionAsesor());
        //riegoActualizar.setObservacionProductor(riegoDto.getObservacionProductor());
        riegoActualizar.setSemanaAplicada(editarRiego.getSemanaAplicada());
        riegoActualizar.setSemanaTransplante(editarRiego.getSemanaTransplante());
        riegoActualizar.setNombreUsuario(usuario);

        try {
            riegoService.save(riegoActualizar);
            return new ResponseEntity(new Mensaje("Riego actualizado"), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(new Mensaje("Fallo la operacion, Riego no actualizado"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
*/