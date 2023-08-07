package com.tesis.backendCuadernoDigital.security.controller;

import com.tesis.backendCuadernoDigital.dto.Mensaje;
import com.tesis.backendCuadernoDigital.excepcion.ExcepcionNoAutorizada;
import com.tesis.backendCuadernoDigital.excepcion.ExcepcionSolicitudIncorrecta;
import com.tesis.backendCuadernoDigital.security.dto.*;
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
import org.springframework.security.authentication.*;
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

    @Autowired
    LogService logService;



    @PostMapping("/login")
    public ResponseEntity<JwtDto> login (@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult){

        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos"), HttpStatus.BAD_REQUEST);
        Usuario usuarioo = usuarioService.getByNombreUsuarioOrEmail(loginUsuario.getNombreUsuario()).get();
        if(!usuarioo.isEstadoActivo())
            return new ResponseEntity(new Mensaje("No se puede loguear usuario inactivo"), HttpStatus.UNAUTHORIZED);

        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(), loginUsuario.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateToken(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Usuario usuario = usuarioService.getByNombreUsuario(userDetails.getUsername()).get();
            JwtDto jwtDto = new JwtDto(jwt);
            logService.guardarLogLoginUsuario(usuario);
            return new ResponseEntity(jwtDto, HttpStatus.OK);

        }catch (InternalAuthenticationServiceException e) {
            return new ResponseEntity(new Mensaje("Usuario Incorrecto"), HttpStatus.UNAUTHORIZED);

        } catch (BadCredentialsException e) {
            Usuario usuario = usuarioService.getByNombreUsuarioOrEmail(loginUsuario.getNombreUsuario()).get();
            Integer cantidadIntentos = 3;
            logService.guardarLogErrorLogin(usuario);
            if(!usuario.getNombreUsuario().equals("admin") && logService.cantidadLogUsuarioMayorOIgualAN(usuario, cantidadIntentos) && logService.ultimosNLogSonErrorLogin(usuario, cantidadIntentos)){
                usuarioService.modificarEstado(usuario.getId());
                logService.guardarLogBajaUsuarioLuegoNIntentoDeAccesoFAllido(usuario);
                throw new ExcepcionSolicitudIncorrecta("El usuario fue dado de baja por exceder los intentos de acceso fallidos: "+cantidadIntentos);
            }
            throw new ExcepcionNoAutorizada("Contraseña Incorrecta");
        }catch (LockedException e){
            //return new ResponseEntity(new Mensaje("Usuario bloqueado"), HttpStatus.UNAUTHORIZED);

            Usuario usuario = usuarioService.getByNombreUsuarioOrEmail(loginUsuario.getNombreUsuario()).get();
            logService.guardarLogErrorLoginUsuarioInactivo(usuario);
            throw new ExcepcionNoAutorizada("El usuario se encuentra bloqueado, solicitar el alta al administrador");
        }

    }

    /*

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<List<Usuario>> list(){
        List<Usuario> list = usuarioService.list();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
*/


    /*
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")int id){
        if(!usuarioService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe el usuario"),HttpStatus.NOT_FOUND);
        usuarioService.delete(id);
        return new ResponseEntity(new Mensaje("Usuario eliminado con exito"),HttpStatus.OK);
    }*/


}
