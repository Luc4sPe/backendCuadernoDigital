package com.tesis.backendCuadernoDigital.controller;

import com.tesis.backendCuadernoDigital.entity.Log;
import com.tesis.backendCuadernoDigital.excepcion.ExcepcionRecursoNoEncontrado;
import com.tesis.backendCuadernoDigital.security.service.UsuarioService;
import com.tesis.backendCuadernoDigital.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logs")
@CrossOrigin("*")
public class LogController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    LogService logService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listado")
    public ResponseEntity<List<Log>> listadoLogs(){
        List<Log> listado = logService.obtenerListado();
        return new ResponseEntity<>(listado, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/actividadUsuario/{idUsuario}")
    public ResponseEntity<List<Log>> listadoLogsPorUsuario(@PathVariable ("idUsuario") int idUsuario){
        if(!usuarioService.existsById(idUsuario))
            throw new ExcepcionRecursoNoEncontrado("No existe el usuario con el ID: "+idUsuario);
        List<Log> listado = logService.logsPorUsuario(idUsuario);
        return new ResponseEntity<>(listado,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/actividad/{nombreUsuario}")
    public ResponseEntity<List<Log>> listadoLogsPorNombreUsuario(@PathVariable ("nombreUsuario") String nombreUsuario){
        if(!usuarioService.existsByNombreUsuario(nombreUsuario))
            throw new ExcepcionRecursoNoEncontrado("No existe ese Nombre de Usuario: "+nombreUsuario);
        List<Log> listado = logService.logsPorNombreUsuario(nombreUsuario);
        return new ResponseEntity<>(listado,HttpStatus.OK);

    }


}
