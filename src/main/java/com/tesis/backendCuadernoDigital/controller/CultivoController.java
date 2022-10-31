package com.tesis.backendCuadernoDigital.controller;

import com.tesis.backendCuadernoDigital.dto.CultivoDto;
import com.tesis.backendCuadernoDigital.dto.Mensaje;
import com.tesis.backendCuadernoDigital.entity.Cultivo;
import com.tesis.backendCuadernoDigital.security.dto.NuevoUsuario;
import com.tesis.backendCuadernoDigital.security.entity.Rol;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import com.tesis.backendCuadernoDigital.security.enums.RolNombre;
import com.tesis.backendCuadernoDigital.security.service.RolService;
import com.tesis.backendCuadernoDigital.security.service.UsuarioService;
import com.tesis.backendCuadernoDigital.service.CultivoService;
import com.tesis.backendCuadernoDigital.service.LogService;
import org.hibernate.type.EnumType;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;


import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Autowired
    RolService rolService;
    @Autowired
    PasswordEncoder passwordEncoder;



    @PreAuthorize("hasAnyRole('ADMIN','ENCARGADO_AGRICOLA')")
    @PostMapping("/crearCultivo")
    public ResponseEntity<?> crearCultivo(@Valid @RequestBody CultivoDto cultivoDto, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal ingresados"), HttpStatus.BAD_REQUEST);

        if (cultivoService.existByRemito(cultivoDto.getRemito()))
            return new ResponseEntity(new Mensaje("Ese remito ya existe"), HttpStatus.BAD_REQUEST);

        if(cultivoDto.getTimpoCarencia()<0)
            return new ResponseEntity(new Mensaje("El timepo de carencia debe ser positiva"), HttpStatus.NOT_ACCEPTABLE);

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
            return new ResponseEntity(new Mensaje("Fallo la operacion, cultivo no registrado"), HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e){
            return new ResponseEntity(new Mensaje("Fallo la operacion, cultivo no registrado"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PreAuthorize("hasAnyRole('ADMIN','ENCARGADO_AGRICOLA', 'PRODUCTOR')")
    @GetMapping("/listado")
    public ResponseEntity<List<Cultivo>> listadoCultivo(){
        List<Cultivo> listado = cultivoService.listadoCultivoPorNombre();
        return new ResponseEntity<>(listado, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','ENCARGADO_AGRICOLA')")
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

    @PreAuthorize("hasAnyRole('ADMIN','ENCARGADO_AGRICOLA')")
    @GetMapping("/detalle/{id}")
    ResponseEntity<Cultivo> obteberDetalleDeUnCultivo(@PathVariable("id") Long id){
        if(!cultivoService.existsByIdCultivo(id))
            return new ResponseEntity(new Mensaje("no existe ese Cultivo"),HttpStatus.NOT_FOUND);
        Cultivo cultivo = cultivoService.getUnCultivo(id).get();
        return new ResponseEntity(cultivo,HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','ENCARGADO_AGRICOLA')")
    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevoProductor(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos o email invalido"), HttpStatus.BAD_REQUEST);

        if(usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario()))
            return new ResponseEntity(new Mensaje("Ese nombre de usuario ya existe"), HttpStatus.BAD_REQUEST);

        if (StringUtils.isBlank(nuevoUsuario.getNombreUsuario()))
            return new ResponseEntity(new Mensaje("El nombre de Usuario no puede estar vacio"), HttpStatus.BAD_REQUEST);

        if (StringUtils.isBlank(nuevoUsuario.getEmail()))
            return new ResponseEntity(new Mensaje("El mail no puede estar vacio"), HttpStatus.BAD_REQUEST);

        if(usuarioService.existsByEmail(nuevoUsuario.getEmail()))
            return new ResponseEntity(new Mensaje("ese email ya existe"), HttpStatus.BAD_REQUEST);

        if(usuarioService.existsByTelefono(nuevoUsuario.getTelefono()))
            return new ResponseEntity(new Mensaje("ese Número de Telefono ya existe"), HttpStatus.BAD_REQUEST);

        Usuario usuario =
                new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getApellido(), nuevoUsuario.getDni(),nuevoUsuario.getNombreUsuario(), nuevoUsuario.getEmail(),
                        nuevoUsuario.getTelefono(),passwordEncoder.encode(nuevoUsuario.getPassword()));
        Set<Rol> roles = new HashSet<>();
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

    @PreAuthorize("hasAnyRole('ADMIN','ENCARGADO_AGRICOLA')")
    @GetMapping("/usuariosPorNombreRol/{nombre}")
    public ResponseEntity<List<Usuario>> listadoUsuarioDeUnRol(@PathVariable ("nombre") String nombre){
        List<Usuario> listadoUsuarioPorRol = usuarioService.listadoUsuarioPorRoles(rolService.getByRolNombre(RolNombre.valueOf(nombre.toUpperCase())));
        return new ResponseEntity<>(listadoUsuarioPorRol,HttpStatus.OK);
    }





}
