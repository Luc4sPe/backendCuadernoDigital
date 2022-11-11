package com.tesis.backendCuadernoDigital.security.controller;


import com.tesis.backendCuadernoDigital.dto.Mensaje;
import com.tesis.backendCuadernoDigital.security.dto.CambioPasswordDto;
import com.tesis.backendCuadernoDigital.security.dto.EditarUsuarioDto;
import com.tesis.backendCuadernoDigital.security.dto.NuevoUsuario;
import com.tesis.backendCuadernoDigital.security.dto.PerfilUsuarioDto;
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
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos o email invalido"), HttpStatus.BAD_REQUEST);
        if(usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario()))
            return new ResponseEntity(new Mensaje("Ese nombre de usuario ya existe"), HttpStatus.BAD_REQUEST);
        if(usuarioService.existsByEmail(nuevoUsuario.getEmail()))
            return new ResponseEntity(new Mensaje("ese email ya existe"), HttpStatus.BAD_REQUEST);
        if(usuarioService.existsByTelefono(nuevoUsuario.getTelefono()))
            return new ResponseEntity(new Mensaje("El número de telefono ya existe"), HttpStatus.BAD_REQUEST);
        Usuario usuario =
                new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getApellido(), nuevoUsuario.getDni(),nuevoUsuario.getNombreUsuario(), nuevoUsuario.getEmail(),
                        nuevoUsuario.getTelefono(),passwordEncoder.encode(nuevoUsuario.getPassword()));
        Set<Rol> roles = new HashSet<>();
        if (nuevoUsuario.getRoles().contains("User"))
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



    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @PutMapping("/update/{id}")
    public ResponseEntity<Usuario> update(@PathVariable("id")Long id, @Valid  @RequestBody EditarUsuarioDto nuevoUsuario, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new Mensaje("Campos mal ingresado"), HttpStatus.BAD_REQUEST);
        }
        if (!usuarioService.existsById(id)) {
            return new ResponseEntity(new Mensaje("no existe el usuario"), HttpStatus.NOT_FOUND);
        }
        Optional<Usuario> usuarioOpcional = usuarioService.getById(id);
        Usuario usuario = usuarioOpcional.get();

        if(usuario.getNombreUsuario().contains("Admin")) {
           return new ResponseEntity(new Mensaje("El usuario administrador no se puede editar"), HttpStatus.BAD_REQUEST);
        }
        if(usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario()) && usuarioService.getByNombreUsuario(nuevoUsuario.getNombreUsuario()).get().getId() != id) {
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        }
        if (usuarioService.existsByEmail(nuevoUsuario.getEmail())&& !usuario.getEmail().equals(nuevoUsuario.getEmail())) {
            return new ResponseEntity(new Mensaje("ese email ya existe"), HttpStatus.BAD_REQUEST);
        }
        if(usuarioService.existsByTelefono(nuevoUsuario.getTelefono())&& !usuario.getTelefono().equals(nuevoUsuario.getTelefono())){
            return  new ResponseEntity(new Mensaje("Ese número de Telefono ya existe"),HttpStatus.BAD_REQUEST);
        }
        if (StringUtils.isBlank(nuevoUsuario.getNombreUsuario())) {
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        }
            usuario.setNombre(nuevoUsuario.getNombre());
            usuario.setApellido(nuevoUsuario.getApellido());
            usuario.setDni(nuevoUsuario.getDni());
            usuario.setNombreUsuario(nuevoUsuario.getNombreUsuario());
            usuario.setEmail(nuevoUsuario.getEmail());
            usuario.setTelefono(nuevoUsuario.getTelefono());
            Set<Rol> roles = new HashSet<>();
            if (nuevoUsuario.getRoles().contains("User"))
                roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
            if (nuevoUsuario.getRoles().contains("Admin"))
                roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
            if (nuevoUsuario.getRoles().contains("Encargado Agricola"))
                roles.add(rolService.getByRolNombre(RolNombre.ROLE_ENCARGADO_AGRICOLA).get());
            if (nuevoUsuario.getRoles().contains("Productor"))
                roles.add(rolService.getByRolNombre(RolNombre.ROLE_PRODUCTOR).get());
       if (nuevoUsuario.getRoles().contains("Gerente"))
           roles.add(rolService.getByRolNombre(RolNombre.ROLE_GERENTE).get());
            usuario.setRoles(roles);
            try {
                Authentication aut = SecurityContextHolder.getContext().getAuthentication();
                Usuario usuarioLogueado = usuarioService.getUsuarioLogeado(aut);
                usuarioService.save(usuario);
                logService.guardarModificacionUsuario(usuario, usuarioLogueado);
                return new ResponseEntity(new Mensaje("usuario actualizado"), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity(new Mensaje("Fallo la operacion, usuario no actualizado"), HttpStatus.INTERNAL_SERVER_ERROR);
            }


    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<List<Usuario>> list(){
        List<Usuario> list = usuarioService.list();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @GetMapping("/detail/{id}")
    public ResponseEntity<Usuario> getByNombre(@PathVariable("id") Long id){
        if(!usuarioService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe el usuario"),HttpStatus.NOT_FOUND);
        Usuario usuario = usuarioService.getById(id).get();
        return new ResponseEntity(usuario,HttpStatus.OK);

    }

    @GetMapping("/detalleNombreUsuario/{nombreUsuario}")
    public ResponseEntity<Usuario> getDetalleUsuarioPorNombreUsuario(@PathVariable("nombreUsuario") String nombreUsuario){
        Optional<Usuario> usuarioOptional = usuarioService.getByNombreUsuario(nombreUsuario);
        if (!usuarioOptional.isPresent()){
            return new ResponseEntity(new Mensaje("El usuario "+nombreUsuario+" no existe"), HttpStatus.BAD_REQUEST);
        }
        Usuario usuario = usuarioService.getByNombreUsuario(nombreUsuario).get();
        return new ResponseEntity(usuario, HttpStatus.OK);

    }


    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @PutMapping("/alta/{id}")
    public ResponseEntity<?> altaUsuario(@PathVariable("id") Long id){
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



    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @PutMapping("/baja/{id}")
    public ResponseEntity<?> bajaUsuario(@PathVariable("id") Long id){
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

    @PreAuthorize("authenticated")
    @PutMapping("/cambioContrasenia/{id}")
    public ResponseEntity<?> cambiarContrasenia(@PathVariable ("id") Long id, @Valid @RequestBody CambioPasswordDto cambioPasswordDto, BindingResult bindingResult){

        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos"), HttpStatus.BAD_REQUEST);

        if(!usuarioService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe el usuario con ID"+id), HttpStatus.BAD_REQUEST);

        Usuario usuario = usuarioService.getById(id).get();

       if(!passwordEncoder.matches(cambioPasswordDto.getPasswordActual(),usuario.getPassword()))
           return new ResponseEntity(new Mensaje("La contraseña ingresada no coincide con la actual"), HttpStatus.BAD_REQUEST);

       if(!cambioPasswordDto.getPasswordNuevo().equals(cambioPasswordDto.getConfirmarPassword()))
           return new ResponseEntity(new Mensaje("La contraseña nueva debe coincidir con la ingresada"), HttpStatus.BAD_REQUEST);

       if(passwordEncoder.matches(cambioPasswordDto.getPasswordNuevo(),usuario.getPassword()))
           return new ResponseEntity(new Mensaje("La contraseña nueva no puede ser igual a la actual"), HttpStatus.BAD_REQUEST);

        try{
            usuario.setPassword(passwordEncoder.encode(cambioPasswordDto.getPasswordNuevo()));
            usuarioService.save(usuario);
            logService.guardarCambioContrasenia(usuario);
            return new ResponseEntity(new Mensaje("Contraseña cambiada correctamente"), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(new Mensaje("Fallo la operacion, contraseña no actualizada"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PutMapping("/actualizarPerfil/{id}")
    public ResponseEntity<Usuario> actualizarPerfil(@PathVariable ("id") Long id, @Valid @RequestBody PerfilUsuarioDto perfilUsuarioDto, BindingResult bindingResult ){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos"), HttpStatus.BAD_REQUEST);

        Optional<Usuario> usuarioOptional = usuarioService.getById(id);

        if (!usuarioOptional.isPresent())
            return new ResponseEntity(new Mensaje("no existe el usuario con ID"+id), HttpStatus.BAD_REQUEST);

        Usuario actualizarPerfilUsuario = usuarioOptional.get();

        if (actualizarPerfilUsuario.getNombreUsuario().contains("admin"))
            return new ResponseEntity(new Mensaje("El administrador no puede ser editado"), HttpStatus.BAD_REQUEST);

        if (usuarioService.existsByNombreUsuario(perfilUsuarioDto.getNombreUsuario()) && !actualizarPerfilUsuario.getNombreUsuario().equals(perfilUsuarioDto.getNombreUsuario()))
            return new ResponseEntity(new Mensaje("El nombre de usuario"+perfilUsuarioDto.getNombreUsuario()+ "ya existe"), HttpStatus.BAD_REQUEST);

        if(usuarioService.existsByEmail(perfilUsuarioDto.getEmail()) && !actualizarPerfilUsuario.getEmail().equals(perfilUsuarioDto.getEmail()))
            return new ResponseEntity(new Mensaje("El Email "+perfilUsuarioDto.getEmail()+ "ya existe"), HttpStatus.BAD_REQUEST);


        actualizarPerfilUsuario.setApellido(perfilUsuarioDto.getApellido());
        actualizarPerfilUsuario.setNombre(perfilUsuarioDto.getNombre());
        actualizarPerfilUsuario.setDni(perfilUsuarioDto.getDni());
        actualizarPerfilUsuario.setNombreUsuario(perfilUsuarioDto.getNombreUsuario());
        actualizarPerfilUsuario.setEmail(perfilUsuarioDto.getEmail());
        actualizarPerfilUsuario.setTelefono(perfilUsuarioDto.getTelefono());

        try {
            usuarioService.save(actualizarPerfilUsuario);
            logService.actualizarPerfil(actualizarPerfilUsuario);
            return new ResponseEntity(actualizarPerfilUsuario, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(new Mensaje("Fallo la operacion, usuario no actualizado"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
