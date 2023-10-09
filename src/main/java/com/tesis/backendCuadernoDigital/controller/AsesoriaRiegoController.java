package com.tesis.backendCuadernoDigital.controller;

import com.tesis.backendCuadernoDigital.dto.AsesoriaRiegoDto;
import com.tesis.backendCuadernoDigital.dto.EditarAplicacionAgroquimicoDto;
import com.tesis.backendCuadernoDigital.dto.Mensaje;
import com.tesis.backendCuadernoDigital.dto.ModificarAsesoriaRiegoDto;
import com.tesis.backendCuadernoDigital.entity.AsesoriaRiego;
import com.tesis.backendCuadernoDigital.entity.Cuadro;
import com.tesis.backendCuadernoDigital.entity.Finca;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import com.tesis.backendCuadernoDigital.security.service.UsuarioService;
import com.tesis.backendCuadernoDigital.service.AsesoriaRiegoService;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/asesoramientoRiego")
@CrossOrigin("*")
public class AsesoriaRiegoController {

    @Autowired
    AsesoriaRiegoService asesoriaRiegoService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    CuadroService cuadroService;

    @Autowired
    FincaService fincaService;

    @Autowired
    LogService logService;

    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @PostMapping("/nuevaAsesoria")
    public ResponseEntity<?> crearAsesoriaRiego(@Valid @RequestBody AsesoriaRiegoDto aseRieDTO, BindingResult bindingResult ){

        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal ingresados"), HttpStatus.BAD_REQUEST);

        if(aseRieDTO.getMilimetrosAplicados()<0)
            return new ResponseEntity(new Mensaje("Los milimetros no puede ser negativo"), HttpStatus.BAD_REQUEST);

        if (aseRieDTO.getIdFinca()<0)
            return new ResponseEntity(new Mensaje("El id de la finca no puede ser negativo"), HttpStatus.BAD_REQUEST);

        try {

            Optional<Cuadro> cuadroOptional = cuadroService.findByIdCuadro(aseRieDTO.getIdCuadro());
            Cuadro cuadroEnviar = cuadroOptional.get();
            Finca finca = fincaService.getFincas(aseRieDTO.getIdFinca());
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = usuarioService.getUsuarioLogeado(auth);
            Optional<Usuario> usuarioOptional = usuarioService.getByNombreUsuario(aseRieDTO.getNombreProductor());
            Usuario usuarioCapturado = usuarioOptional.get();
            AsesoriaRiego nuevaAsesoriaRiego = new AsesoriaRiego(aseRieDTO.getDuracionEnHoras(),aseRieDTO.getMilimetrosAplicados(),finca,cuadroEnviar ,usuarioCapturado,
                    aseRieDTO.getFechaEstimadaAplicacion());

            /*
            // recorre la lista para ir guardando cada asesoria en un cuadro de la lista
            List<Cuadro> cuadros = aseRieDTO.getNumerosDeCuadros()
                    .stream()
                    .map(cuadro -> cuadroService.getCuadro(cuadro.getIdCuadro()))
                    .distinct()
                    .collect(Collectors.toList());
            nuevaAsesoriaRiego.setNumerosDeCuadros(cuadros);

             */

            this.asesoriaRiegoService.guardarAsesoramientoRiego(nuevaAsesoriaRiego);

            if (nuevaAsesoriaRiego!=null){
                logService.guardarAsesoriaRiego(nuevaAsesoriaRiego,usuario);
                return new ResponseEntity<>(new Mensaje("La asesoria de riego se guardo correctamente"), HttpStatus.CREATED);
            }
            return new ResponseEntity(new Mensaje("Fallo la operacion, asesoria de riego no registrado"), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e){
            return new ResponseEntity(new Mensaje("Fallo la operacion, asesoria de riego no registrado"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA')")
    @PutMapping("/editarAsesoriaRiego/{id}")
    public ResponseEntity<?> editarAsesoriaRiego(@PathVariable("id")Long id, @RequestBody ModificarAsesoriaRiegoDto modiAseRiegoDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("Campos mal ingresado"), HttpStatus.BAD_REQUEST);

        //if (!asesoriaRiegoService.existeByIdAsesoriaRiego(modiAseRiegoDto.getId()))
          //  return new ResponseEntity(new Mensaje("No existe esa asesoria de riego"), HttpStatus.NOT_FOUND);

        if(modiAseRiegoDto.getMilimetrosAplicados()<0)
            return new ResponseEntity(new Mensaje("Los milimetros no puede ser negativo"), HttpStatus.BAD_REQUEST);

        //se obtiene una asesoría a traves del id para manupular uno de los atributo de la clase en este caso el estado
        AsesoriaRiego asesoriaRiego = asesoriaRiegoService.getAsesoriaRiego(id);
        if(asesoriaRiego.isAsesoriaAplicada()==true)
            return new ResponseEntity(new Mensaje("La asesoria fue aplicada no se puede modificar "), HttpStatus.BAD_REQUEST);

        if( asesoriaRiego.getFechaModificacionAsesoriaRiego()!=null )
            return new ResponseEntity(new Mensaje("La asesoría ya fue modificada "), HttpStatus.BAD_REQUEST);
        try {

            Optional<Usuario> usuarioOptional = usuarioService.getByNombreUsuario(modiAseRiegoDto.getNombreProductor());
            Usuario usuarioCapturado = usuarioOptional.get();
            Finca finca = fincaService.getFincas(modiAseRiegoDto.getIdFinca());
            Optional<Cuadro> cuadroOptional = cuadroService.findByIdCuadro(modiAseRiegoDto.getIdCuadro());
            Cuadro getIdCuadro = cuadroOptional.get();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = usuarioService.getUsuarioLogeado(auth);
            /*
            // recorre la lista para ir modificando cada asesoria en un cuadro de la lista
            List<Cuadro> cuadros = modiAseRiegoDto.getNumerosDeCuadros()
                    .stream()
                    .map(cuadro -> cuadroService.getCuadro(cuadro.getIdCuadro()))
                    .distinct()
                    .collect(Collectors.toList());

             */
            AsesoriaRiego modificar = asesoriaRiegoService.getUnaAsesoriaRiego(id).get();
            modificar.setDuracionEnHoras(modiAseRiegoDto.getDuracionEnHoras());
            modificar.setMilimetrosAplicados(modiAseRiegoDto.getMilimetrosAplicados());
            modificar.setFinca(finca);
            modificar.setCuadro(getIdCuadro);
            modificar.setProductor(usuarioCapturado);
            modificar.fechaModificacionAsesoriaRiego();
            modificar.setFechaEstimadaAplicacion(modiAseRiegoDto.getFechaEstimadaAplicacion());

            asesoriaRiegoService.actualizarAsesoriaRiego(modificar);
            if(modificar!=null){
                logService.modificarAsesoriaRiego(modificar,usuario);
                return new ResponseEntity<>(new Mensaje(" Asesoria de riego actualizada correctamente"), HttpStatus.OK);
            }
            return new ResponseEntity(new Mensaje("Fallo la operacion, asesoria de riego no actualizado"), HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e){
            return new ResponseEntity(new Mensaje("Fallo la operacion, asesoria de riego no actualizado"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }




    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO_AGRICOLA', 'PRODUCTOR')")
    @GetMapping("/listaAsesoria")
    public ResponseEntity<List<AsesoriaRiego>> listaAsesoriaRiego(){
        List<AsesoriaRiego> listar = asesoriaRiegoService.listarAsesoriaRiego();
        return new ResponseEntity<>(listar,HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PRODUCTOR', 'ENCARGADO_AGRICOLA')")
    @GetMapping("/listadoAsesoriaRiegoDeUnaFinca/{idFinca}")
    public ResponseEntity<List<Cuadro>> listadoAsesoriaRiegoDeUnaFinca(@PathVariable ("idFinca") Long idFinca){
        Finca finca = fincaService.getFincas(idFinca);
        List<AsesoriaRiego> asesoria = asesoriaRiegoService.getListadoAsesoriaRiegoDeUnaFincaPorId(finca.getIdFinca());
        return new ResponseEntity(asesoria,HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PRODUCTOR', 'ENCARGADO_AGRICOLA')")
    @GetMapping("/listadoAsesoriaRiegoDeUnProductor/{nombre}")
    public ResponseEntity<List<AsesoriaRiego>> listadoAsesoriaRiegoDeUnProductor(@PathVariable ("nombre") String nombre){
        List<AsesoriaRiego> asesoria = asesoriaRiegoService.getListadoAsesoriaRiegoPorNombreUsuario(nombre);
        return new ResponseEntity(asesoria,HttpStatus.OK);
    }




    @PreAuthorize("hasAnyRole('ADMIN', 'PRODUCTOR', 'ENCARGADO_AGRICOLA')")
    @PutMapping("/aplico/{id}")
    public ResponseEntity<?> aplicar(@PathVariable("id") Long id){

        if(!asesoriaRiegoService.existeByIdAsesoriaRiego(id)){
            return new ResponseEntity(new Mensaje("La asesoria de riego no existe"), HttpStatus.NOT_FOUND);
        }

        AsesoriaRiego asesoriaRiego = asesoriaRiegoService.getUnaAsesoriaRiego(id).get();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioLogueado = usuarioService.getUsuarioLogeado(auth);
        if (asesoriaRiego.isAsesoriaAplicada()) {
            return new ResponseEntity(new Mensaje("La asesoria de riego ya se aplico"), HttpStatus.BAD_REQUEST);
        }
        asesoriaRiegoService.modificarEstado(id);


        logService.modificarEstadoAsesoriaRiegoTrue(asesoriaRiego,usuarioLogueado);
        return  new ResponseEntity(new Mensaje("Se aplicó exitosamente la asesoria de riego "),HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PRODUCTOR', 'ENCARGADO_AGRICOLA')")
    @PutMapping("/canceloAplica/{id}")
    public ResponseEntity<?> cancelarAplica(@PathVariable("id") Long id){

        if(!asesoriaRiegoService.existeByIdAsesoriaRiego(id)){
            return new ResponseEntity(new Mensaje("La asesoria de riego no existe"), HttpStatus.NOT_FOUND);
        }

        AsesoriaRiego asesoriaRiego = asesoriaRiegoService.getUnaAsesoriaRiego(id).get();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioLogueado = usuarioService.getUsuarioLogeado(auth);

        if (!asesoriaRiego.isAsesoriaAplicada()) {
            return new ResponseEntity(new Mensaje("La asesoria de riego no se aplico"), HttpStatus.BAD_REQUEST);
        }

        asesoriaRiegoService.modificarEstado(id);
        logService.modificarEstadoAsesoriaRiegoFalse(asesoriaRiego,usuarioLogueado);
        return  new ResponseEntity(new Mensaje("Se canceló exitosamente la asesoria de riego "),HttpStatus.OK);
    }





    @PreAuthorize("hasAnyRole('ADMIN','PRODUCTOR','ENCARGADO_AGRICOLA')")
    @GetMapping("/detalle/{id}")
    ResponseEntity<AsesoriaRiego> obteberDetalleDeUnaAsesoria(@PathVariable("id") Long id){

        if(!asesoriaRiegoService.existeByIdAsesoriaRiego(id))
            return new ResponseEntity(new Mensaje("no existe esa asesoria"),HttpStatus.NOT_FOUND);
        AsesoriaRiego asesoria = asesoriaRiegoService.getUnaAsesoriaRiego(id).get();
        return new ResponseEntity(asesoria,HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('ADMIN','PRODUCTOR','ENCARGADO_AGRICOLA')")
    @GetMapping("/total")
    public ResponseEntity<?> cantidadTotalAsesoria(){
        Integer cantidad = asesoriaRiegoService.obtenerCantidadAsesoriasRiego();
        return new ResponseEntity(cantidad, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','PRODUCTOR','ENCARGADO_AGRICOLA')")
    @GetMapping("/total-Aplicados")
    public ResponseEntity<?> cantidadTotalDeAsesoriaAplicada(){
        Integer cantidad = asesoriaRiegoService.cantidadDeAplicacionAsesoria();
        return new ResponseEntity(cantidad, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','PRODUCTOR','ENCARGADO_AGRICOLA')")
    @GetMapping("/total-NoAplicados")
    public ResponseEntity<?> cantidadTotalDeAsesoriaNoAplicada(){
        Integer cantidad = asesoriaRiegoService.cantidadDeAsesoriaNoAplicada();
        return new ResponseEntity(cantidad, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','PRODUCTOR','ENCARGADO_AGRICOLA')")
    @GetMapping("/total-asesoriaByProductor/{nombre}")
    public ResponseEntity<?> cantidadTotalAsesoriaByProductor(@PathVariable ("nombre") String nombre){
        Integer cantidad = asesoriaRiegoService.obtenerTotalAsesoriaByProductor(usuarioService.getByNombreUsuario(nombre));
        return new ResponseEntity(cantidad, HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('ADMIN','PRODUCTOR','ENCARGADO_AGRICOLA')")
    @GetMapping("/total-asesoriaAplicadaByProductor/{nombre}")
    public ResponseEntity<?> cantidadTotalAsesoriaAplicadaByProductor(@PathVariable ("nombre") String nombre){
        Integer cantidad = asesoriaRiegoService.obtenerTotalAsesoriaAplicadaByProductor(usuarioService.getByNombreUsuario(nombre));
        return new ResponseEntity(cantidad, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','PRODUCTOR','ENCARGADO_AGRICOLA')")
    @GetMapping("/total-asesoriaNoAplicadaByProductor/{nombre}")
    public ResponseEntity<?> cantidadTotalAsesoriaNoAplicadaByProductor(@PathVariable ("nombre") String nombre){
        Integer cantidad = asesoriaRiegoService.obtenerTotalAsesoriaNoAplicadaByProductor(usuarioService.getByNombreUsuario(nombre));
        return new ResponseEntity(cantidad, HttpStatus.OK);
    }







}


