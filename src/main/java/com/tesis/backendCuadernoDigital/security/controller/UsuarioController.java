package com.tesis.backendCuadernoDigital.security.controller;


import com.tesis.backendCuadernoDigital.dto.Mensaje;
import com.tesis.backendCuadernoDigital.security.dto.EditarUsuarioDto;
import com.tesis.backendCuadernoDigital.security.dto.NuevoUsuario;
import com.tesis.backendCuadernoDigital.security.entity.Rol;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import com.tesis.backendCuadernoDigital.security.enums.RolNombre;
import com.tesis.backendCuadernoDigital.security.jwt.JwtProvider;
import com.tesis.backendCuadernoDigital.security.service.RolService;
import com.tesis.backendCuadernoDigital.security.service.UsuarioService;
import com.tesis.backendCuadernoDigital.service.LogService;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Autowired
    LogService logService;


    // @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos o email invalido"), HttpStatus.BAD_REQUEST);
        if(usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario()))
            return new ResponseEntity(new Mensaje("Ese nombre de usuario ya existe"), HttpStatus.BAD_REQUEST);
        if(usuarioService.existsByEmail(nuevoUsuario.getEmail()))
            return new ResponseEntity(new Mensaje("ese email ya existe"), HttpStatus.BAD_REQUEST);
        Usuario usuario =
                new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getApellido(), nuevoUsuario.getDni(),nuevoUsuario.getNombreUsuario(), nuevoUsuario.getEmail(),
                        passwordEncoder.encode(nuevoUsuario.getPassword()));
        Set<Rol> roles = new HashSet<>();
        if (nuevoUsuario.getRoles().contains("Usuario"))
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
        if(nuevoUsuario.getRoles().contains("Admin"))
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        if(nuevoUsuario.getRoles().contains("Gerente"))
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_GERENTE).get());
        if(nuevoUsuario.getRoles().contains("Encargado Agricola"))
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ENCARGADO_AGRICOLA).get());
        if (nuevoUsuario.getRoles().contains("Productor"))
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_PRODUCTOR).get());
        usuario.setRoles(roles);
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuarioLogeado =  usuarioService.getUsuarioLogeado(auth);
            usuarioService.save(usuario);
            logService.guardarLogCreacionUsuario(usuario,usuarioLogeado);
            return new ResponseEntity(new Mensaje(" Usuario Registrado con Exito"), HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity(new Mensaje("Fallo la operacion, usuario no Registrado"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



   // @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id")int id, @RequestBody EditarUsuarioDto nuevoUsuario, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new Mensaje("Campos mal ingresado"), HttpStatus.BAD_REQUEST);
        }
        if (!usuarioService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe el usuario"), HttpStatus.NOT_FOUND);
        if (usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario()) && usuarioService.getByNombreUsuario(nuevoUsuario.getNombreUsuario()).get().getId() != id)
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        //if (usuarioService.existsByEmail(nuevoUsuario.getEmail()))
        //  return new ResponseEntity(new Mensaje("ese email ya existe"), HttpStatus.BAD_REQUEST);

        if (StringUtils.isBlank(nuevoUsuario.getNombreUsuario()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        {

            Usuario usuario = usuarioService.getById(id).get();
            Set<Rol> roles = new HashSet<>();

            if (nuevoUsuario.getRoles().contains("Admin"))
                roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());

            if (nuevoUsuario.getRoles().contains("Encargado Agricola"))
                roles.add(rolService.getByRolNombre(RolNombre.ROLE_ENCARGADO_AGRICOLA).get());

            if (nuevoUsuario.getRoles().contains("Productor")) ;
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_PRODUCTOR).get());

            roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
            usuario.setRoles(roles);
            usuario.setNombre(nuevoUsuario.getNombre());
            usuario.setApellido(nuevoUsuario.getApellido());
            usuario.setDni(nuevoUsuario.getDni());
            usuario.setNombreUsuario(nuevoUsuario.getNombreUsuario());
            usuario.setEmail(nuevoUsuario.getEmail());
            usuario.setRoles(roles);
            try {
                Authentication aut = SecurityContextHolder.getContext().getAuthentication();
                Usuario usuarioLogueado = usuarioService.getUsuarioLogeado(aut);
                usuarioService.save(usuario);
                logService.guardarModificacionUsuario(usuario,usuarioLogueado);
                return new ResponseEntity(new Mensaje("usuario actualizado"), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity(new Mensaje("Fallo la operacion, usuario no actualizado"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<List<Usuario>> list(){
        List<Usuario> list = usuarioService.list();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }


    @GetMapping("/detail/{id}")
    public ResponseEntity<Usuario> getByNombre(@PathVariable("id") int id){
        if(!usuarioService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe el usuario"),HttpStatus.NOT_FOUND);
        Usuario usuario = usuarioService.getById(id).get();
        return new ResponseEntity(usuario,HttpStatus.OK);

    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/alta/{id}")
    public ResponseEntity<?> altaUsuario(@PathVariable("id") int id){
        if (!usuarioService.existsById(id)){
            return new ResponseEntity(new Mensaje("El usuario no existe"), HttpStatus.NOT_FOUND);
        }
        Usuario usuario = usuarioService.getById(id).get();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioLogueado = usuarioService.getUsuarioLogeado(auth);
        if (usuario.isEstadoActivo()) {
            return new ResponseEntity(new Mensaje("El usuario ya se encuentra Activo"), HttpStatus.BAD_REQUEST);
        }
        usuarioService.modificarEstado(id);
        logService.guardarAltaUsuario(usuario,usuarioLogueado);
        return  new ResponseEntity(new Mensaje("Usuario dado de Alta exitosamente "),HttpStatus.OK);
    }



    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/baja/{id}")
    public ResponseEntity<?> bajaUsuario(@PathVariable("id") int id){
        if (!usuarioService.existsById(id)){
            return new ResponseEntity(new Mensaje("El usuario no existe"), HttpStatus.NOT_FOUND);
        }
        Usuario usuario = usuarioService.getById(id).get();
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioLogueado = usuarioService.getUsuarioLogeado(auth);
        if (!usuario.isEstadoActivo()) {
            return new ResponseEntity(new Mensaje("El usuario ya se encuentra Dado de baja"), HttpStatus.BAD_REQUEST);
        }
        if(usuario.getNombreUsuario().contains("admin"))
            return new ResponseEntity<>(new Mensaje("No puede darse de baja el administrador"),HttpStatus.BAD_REQUEST);
        usuarioService.modificarEstado(id);
        logService.guardarBajaUsuario(usuario,usuarioLogueado);
        return  new ResponseEntity(new Mensaje("Usuario dado de Baja exitosamente"),HttpStatus.OK);
    }



}
