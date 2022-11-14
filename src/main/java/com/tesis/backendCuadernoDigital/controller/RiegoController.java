package com.tesis.backendCuadernoDigital.controller;


import com.tesis.backendCuadernoDigital.dto.EditarRiegoDto;
import com.tesis.backendCuadernoDigital.dto.Mensaje;
import com.tesis.backendCuadernoDigital.dto.RiegoDto;
import com.tesis.backendCuadernoDigital.entity.Cuadro;
import com.tesis.backendCuadernoDigital.entity.Finca;
import com.tesis.backendCuadernoDigital.entity.Riego;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import com.tesis.backendCuadernoDigital.security.service.UsuarioService;
import com.tesis.backendCuadernoDigital.service.CuadroService;
import com.tesis.backendCuadernoDigital.service.FincaService;
import com.tesis.backendCuadernoDigital.service.LogService;
import com.tesis.backendCuadernoDigital.service.RiegoService;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
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

    @Autowired
    CuadroService cuadroService;

    @Autowired
    FincaService fincaService;

    @Autowired
    LogService logService;


    @PreAuthorize("hasAnyRole('ADMIN', 'PRODUCTOR')")
    @PostMapping("/nuevoRiego")
    public ResponseEntity<?> crearRiego(@Valid @RequestBody RiegoDto riegoDto, BindingResult bindingResult ){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal ingresados"), HttpStatus.BAD_REQUEST);

       //if(riegoService.existsByIdRiego(riegoDto.getId()))
         //   return new ResponseEntity(new Mensaje("Ese Riego ya existe"), HttpStatus.BAD_REQUEST);

        if(riegoDto.getMilimetrosAplicados()<0)
            return new ResponseEntity(new Mensaje("Los milimetros no puede ser negativo"), HttpStatus.BAD_REQUEST);

        if (riegoDto.getIdCuadro()<0)
            return new ResponseEntity(new Mensaje("El id del cuadro no puede ser negativo"), HttpStatus.BAD_REQUEST);

        if (StringUtils.isBlank(riegoDto.getObservacionProductor()))
            return new ResponseEntity(new Mensaje("La observacion es obligatoria"), HttpStatus.BAD_REQUEST);

        if (riegoDto.getIdFinca()<0)
            return new ResponseEntity(new Mensaje("El id de la finca no puede ser negativo"), HttpStatus.BAD_REQUEST);


        try {

            Optional<Cuadro> cuadroOptional = cuadroService.findByIdCuadro(riegoDto.getIdCuadro());
            Cuadro cuadroEnviar = cuadroOptional.get();
            Finca finca = fincaService.getFincas(riegoDto.getIdFinca());
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = usuarioService.getUsuarioLogeado(auth);

            Riego nuevoRiego = new Riego(riegoDto.getDuracionEnHoras(),riegoDto.getMilimetrosAplicados(),cuadroEnviar,
                    riegoDto.getObservacionProductor(),"",finca);



            this.riegoService.guardarRiego(nuevoRiego);

            if (nuevoRiego!=null){
                logService.guardarRiego(nuevoRiego,usuario);
                return new ResponseEntity<>(new Mensaje("El riego se guardado correctamente"), HttpStatus.CREATED);
            }
            return new ResponseEntity(new Mensaje("Fallo la operacion, riego no Registrado"), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e){
            return new ResponseEntity(new Mensaje("Fallo la operacion, Riego no registrado"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
    Error a corregir al mostrar todos los riegos
    @PreAuthorize("hasAnyRole('ADMIN', 'PRODUCTOR','ENCARGADO_AGRICOLA')")
    @GetMapping("/listadoRiego")
    public ResponseEntity<List<Riego>> listadoRiego(){
        List<Riego> listado = riegoService.listaRiegos();
        return new ResponseEntity<>(listado, HttpStatus.OK);
    }

     */


    @PreAuthorize("hasAnyRole('ADMIN', 'PRODUCTOR')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id")Long id, @RequestBody EditarRiegoDto editarRiego, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("Campos mal ingresado"), HttpStatus.BAD_REQUEST);

        //if(!riegoService.existsByIdRiego(editarRiego.getId()))
          //  return new ResponseEntity(new Mensaje("no existe ese Riego"), HttpStatus.NOT_FOUND);

        if(editarRiego.getMilimetrosAplicados()<0)
            return new ResponseEntity(new Mensaje("Los milimetros no puede ser negativo"), HttpStatus.BAD_REQUEST);

        if (editarRiego.getCuadro()<0)
            return new ResponseEntity(new Mensaje("El id del cuadro no puede ser negativo"), HttpStatus.BAD_REQUEST);

        if (StringUtils.isBlank(editarRiego.getObservacionProductor()))
            return new ResponseEntity(new Mensaje("La observacion es obligatoria"), HttpStatus.BAD_REQUEST);

        if (StringUtils.isBlank(editarRiego.getJustificacionProductor()))
            return new ResponseEntity(new Mensaje("Tiene que declarar una justificación"), HttpStatus.BAD_REQUEST);



        Optional<Riego> riegoOptional = riegoService.getUnRiegoById(id);
        Riego justificacion = riegoOptional.get();


        if (!justificacion.getJustificacionProductor().isEmpty())
            return new ResponseEntity(new Mensaje("El archivo ya ha sido modificado anteriormente "), HttpStatus.BAD_REQUEST);
        
        try {

            Optional<Cuadro> cuadroOptional = cuadroService.findByIdCuadro(editarRiego.getCuadro());
            Cuadro getIdCuadro = cuadroOptional.get();

           //Finca finca = fincaService.getFincas(editarRiego.getIdFinca());
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = usuarioService.getUsuarioLogeado(auth);

            Riego riegoActualizar = riegoService.getUnRiegoById(id).get();

            riegoActualizar.setDuracionEnHoras(editarRiego.getDuracionEnHoras());
            riegoActualizar.setMilimetrosAplicados(editarRiego.getMilimetrosAplicados());
            riegoActualizar.setIdCuadro(getIdCuadro);
            riegoActualizar.setObservacionProductor(editarRiego.getObservacionProductor());
            riegoActualizar.setJustificacionProductor(editarRiego.getJustificacionProductor());


            riegoService.modificarRiego(riegoActualizar);

            if(riegoActualizar!=null){
                logService.modificarRiego(riegoActualizar,usuario);
                return new ResponseEntity<>(new Mensaje(" Riego actualizado correctamente"), HttpStatus.OK);
            }
            return new ResponseEntity(new Mensaje("Fallo la operacion, riego no actualizado"), HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e){
            return new ResponseEntity(new Mensaje("Fallo la operacion, riego no actualizado"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PreAuthorize("hasAnyRole('ADMIN', 'PRODUCTOR')")
    @GetMapping("/detalle/{id}")
    public ResponseEntity<Riego> obteberDetalleDeUnRiego(@PathVariable("id") Long id){
        if(!riegoService.existsByIdRiego(id))
            return new ResponseEntity(new Mensaje("no existe el riego"),HttpStatus.NOT_FOUND);
        Riego riego = riegoService.getUnRiegoById(id).get();
        return new ResponseEntity(riego,HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'PRODUCTOR')")
    @GetMapping("/listadoRiegoDeUnaFinca/{idFinca}")
    public ResponseEntity<List<Cuadro>> listadoRiegoDeUnaFinca(@PathVariable ("idFinca") Long idFinca){
        Finca finca = fincaService.getFincas(idFinca);
        List<Riego> riego = riegoService.getListadoRiegosDeUnaFincaPorId(finca.getIdFinca());
        return new ResponseEntity(riego,HttpStatus.OK);
    }




/*







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




 */


}
