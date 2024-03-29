package com.tesis.backendCuadernoDigital.controller;

import com.tesis.backendCuadernoDigital.dto.FincaDto;
import com.tesis.backendCuadernoDigital.dto.Mensaje;
import com.tesis.backendCuadernoDigital.entity.Cuadro;
import com.tesis.backendCuadernoDigital.entity.Finca;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import com.tesis.backendCuadernoDigital.security.service.UsuarioService;
import com.tesis.backendCuadernoDigital.service.CuadroService;
import com.tesis.backendCuadernoDigital.service.FincaService;
import com.tesis.backendCuadernoDigital.service.LogService;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/finca")
@CrossOrigin("*")
public class FincaController {
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    LogService logService;

    @Autowired
    CuadroService cuadroService;

    @Autowired
    FincaService fincaService;

    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @PostMapping("/crearFinca")
    public ResponseEntity<?> crearFinca(@Valid @RequestBody FincaDto fincaDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal ingresados"), HttpStatus.BAD_REQUEST);

        if (fincaService.existsByNombreFinca(fincaDto.getNombre()))
            return new ResponseEntity(new Mensaje("Esa Finca ya existe"), HttpStatus.BAD_REQUEST);

        if (StringUtils.isBlank(fincaDto.getDireccion()))
            return new ResponseEntity(new Mensaje("La observacion es obligatoria"), HttpStatus.BAD_REQUEST);

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = usuarioService.getUsuarioLogeado(auth);

            Optional<Usuario> usuarioOptional = usuarioService.getByNombreUsuario(fincaDto.getNombreProductor());
            Usuario usuarioCapturado = usuarioOptional.get();


            Usuario productor= usuarioService.getByNombreUsuario(fincaDto.getNombreProductor()).get();
            Finca nuevaFinca = new Finca(fincaDto.getNombre(), fincaDto.getDireccion(), usuarioCapturado);

            //nuevaFinca.setProductor(productor);

            List<Cuadro> cuadros = fincaDto.getCuadros()
                    .stream()
                    .map(cuadro -> new Cuadro(cuadro.getNumeroCuadro(), cuadro.getSuperficieHectarea(),cuadro.getFinca()))
                    .collect(Collectors.toList());
            nuevaFinca.setCuadros(cuadros);

            boolean resultado = fincaService.guardarFinca(nuevaFinca);
            if (resultado) {
                logService.guardarFinca(nuevaFinca, usuario);
                return new ResponseEntity<>(new Mensaje("La finca se guardado correctamente"), HttpStatus.CREATED);
            }
            return new ResponseEntity(new Mensaje("Fallo la operacion, Finca no Registrada"), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity(new Mensaje("Fallo la operacion, Finca no Registrada"), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @GetMapping("/listadoFinca")
    public ResponseEntity<List<Finca>> listadoFinca() {
        List<Finca> listado = fincaService.listarFinca();
        return new ResponseEntity<>(listado, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENCARGADO_AGRICOLA','PRODUCTOR')")
    @GetMapping("/fincaPorNombreUsuario/{nombreUsuario}")
    public ResponseEntity<List<Finca>> listadoFincaPorUsuario(@PathVariable("nombreUsuario") String nombreUsuario) {
        List<Finca> listadoPorUsuario = fincaService.listadoFincaDeUnUsuarioPorNombreUsuario(nombreUsuario);
        return new ResponseEntity<>(listadoPorUsuario, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @PutMapping("/modificar/{id}")
    public ResponseEntity<?> modificarFinca(@PathVariable("id") Long id, @Valid @RequestBody FincaDto fincaDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal ingresados"), HttpStatus.BAD_REQUEST);

        if (!fincaService.existsByIdFinca(id))
            return new ResponseEntity(new Mensaje("no existe esa Finca"), HttpStatus.NOT_FOUND);

        //if (fincaService.existsByNombreFinca(fincaDto.getNombre()))
          //  return new ResponseEntity(new Mensaje("Esa Finca ya existe"), HttpStatus.BAD_REQUEST);

        if (StringUtils.isBlank(fincaDto.getDireccion()))
            return new ResponseEntity(new Mensaje("La observacion es obligatoria"), HttpStatus.BAD_REQUEST);


        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = usuarioService.getUsuarioLogeado(auth);

            Optional<Usuario> usuarioOptional = usuarioService.getByNombreUsuario(fincaDto.getNombreProductor());
            Usuario usuarioCapturado = usuarioOptional.get();

            List<Cuadro> cuadros = fincaDto.getCuadros()
                    .stream()
                    .map(cuadro -> cuadroService.obtenerCuadro(cuadro.getNumeroCuadro()))
                    .collect(Collectors.toList());


            Finca modificarFinca = fincaService.getFinca(id).get();
            modificarFinca.setNombre(fincaDto.getNombre());
            modificarFinca.setDireccion(fincaDto.getDireccion());
           // modificarFinca.setLongitud(fincaDto.getLongitud());
           // modificarFinca.setLatitud(fincaDto.getLatitud());
            modificarFinca.setProductor(usuarioCapturado);
            //modificarFinca.setCuadros(cuadros);
            fincaService.guardarFinca(modificarFinca);

            if (modificarFinca != null) {
                logService.modificarFinca(modificarFinca, usuario);
                return new ResponseEntity<>(new Mensaje(" Finca actualizada correctamente"), HttpStatus.OK);
            }
            return new ResponseEntity(new Mensaje("Fallo la operacion, Finca no Modificada"), HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception e) {
            return new ResponseEntity(new Mensaje("Fallo la operacion, Finca no Modificada"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @GetMapping("/CantidadDeFinca")
    public ResponseEntity<Integer> cantidadTotalFincas() {
        Integer cantidad = fincaService.getCantidadDeFincas();
        return new ResponseEntity<>(cantidad, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ENCARGADO_AGRICOLA','PRODUCTOR','ADMIN')")
    @GetMapping("/detalle/{id}")
    ResponseEntity<Finca> obteberDetalleDeUnaFinca(@PathVariable("id") Long id) {
        if (!fincaService.existsByIdFinca(id))
            return new ResponseEntity(new Mensaje("no existe la finca"), HttpStatus.NOT_FOUND);
        Finca finca = fincaService.getFinca(id).get();
        return new ResponseEntity(finca, HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @GetMapping("/listadoCuadro")
    public ResponseEntity<List<Cuadro>> listadoCuadro() {
        List<Cuadro> listado = cuadroService.listarCuadros();
        return new ResponseEntity<>(listado, HttpStatus.OK);


    }


}
