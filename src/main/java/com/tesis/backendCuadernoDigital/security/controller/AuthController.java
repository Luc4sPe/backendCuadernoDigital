package com.tesis.backendCuadernoDigital.security.controller;

import com.tesis.backendCuadernoDigital.dto.Mensaje;
import com.tesis.backendCuadernoDigital.security.dto.JwtDto;
import com.tesis.backendCuadernoDigital.security.dto.LoginUsuario;
import com.tesis.backendCuadernoDigital.security.dto.NuevoUsuario;
import com.tesis.backendCuadernoDigital.security.entity.Rol;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import com.tesis.backendCuadernoDigital.security.enums.RolNombre;
import com.tesis.backendCuadernoDigital.security.jwt.JwtProvider;
import com.tesis.backendCuadernoDigital.security.service.RolService;
import com.tesis.backendCuadernoDigital.security.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//Api Rest
@RestController
@RequestMapping ("/auth")
@CrossOrigin("*")
public class AuthController {

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

    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos o email invalido"), HttpStatus.BAD_REQUEST);
        if(usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario()))
            return new ResponseEntity(new Mensaje("Ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        if(usuarioService.existsByEmail(nuevoUsuario.getEmail()))
            return new ResponseEntity(new Mensaje("ese email ya existe"), HttpStatus.BAD_REQUEST);
        Usuario usuario =
                new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getApellido(), nuevoUsuario.getNombreUsuario(), nuevoUsuario.getEmail(),
                        passwordEncoder.encode(nuevoUsuario.getPassword()));
        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
        if(nuevoUsuario.getRoles().contains("admin"))
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        usuario.setRoles(roles);
        usuarioService.save(usuario);
        return new ResponseEntity(new Mensaje(" Usuario Registrado con Exito"), HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login (@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult){

        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos"), HttpStatus.BAD_REQUEST);
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(), loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        return new ResponseEntity(jwtDto, HttpStatus.OK);


    }
    //@PreAuthorize("hasRole('ADMIN')")
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

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")int id){
        if(!usuarioService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe el usuario"),HttpStatus.NOT_FOUND);
        usuarioService.delete(id);
        return new ResponseEntity(new Mensaje("Usuario eliminado con exito"),HttpStatus.OK);
    }

}
