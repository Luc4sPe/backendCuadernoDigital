package com.tesis.backendCuadernoDigital.security.controller;


import com.tesis.backendCuadernoDigital.dto.Mensaje;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import com.tesis.backendCuadernoDigital.security.jwt.JwtProvider;
import com.tesis.backendCuadernoDigital.security.service.RolService;
import com.tesis.backendCuadernoDigital.security.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Api Rest
@RestController
@RequestMapping("/usuario")
@CrossOrigin("*")
public class UsuarioController {


    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;


    @GetMapping("/list")
    public ResponseEntity<List<Usuario>> list(){
        List<Usuario> list = usuarioService.list();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }



   // @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/alta/{id}")
    public ResponseEntity<?> altaUsuario(@PathVariable("id") int id){
        if (!usuarioService.existsById(id)){
            return new ResponseEntity(new Mensaje("El usuario no existe"), HttpStatus.NOT_FOUND);
        }
        Usuario usuario = usuarioService.getById(id).get();
        if (usuario.isEstadoActivo()) {
            return new ResponseEntity(new Mensaje("El usuario ya se encuentra Activo"), HttpStatus.BAD_REQUEST);
        }
        usuarioService.modificarEstado(id);
        return  new ResponseEntity(new Mensaje("Usuario dado de Alta exitosamente "),HttpStatus.OK);
    }
    //@PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/baja/{id}")
    public ResponseEntity<?> bajaUsuario(@PathVariable("id") int id){
        if (!usuarioService.existsById(id)){
            return new ResponseEntity(new Mensaje("El usuario no existe"), HttpStatus.NOT_FOUND);
        }
        Usuario usuario = usuarioService.getById(id).get();
        if (!usuario.isEstadoActivo()) {
            return new ResponseEntity(new Mensaje("El usuario ya se encuentra Dado de baja"), HttpStatus.BAD_REQUEST);
        }
        usuarioService.modificarEstado(id);
        return  new ResponseEntity(new Mensaje("Usuario dado de Baja exitosamente"),HttpStatus.OK);
    }



}