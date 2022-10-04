package com.tesis.backendCuadernoDigital.service;

import com.tesis.backendCuadernoDigital.entity.Cuadro;
import com.tesis.backendCuadernoDigital.excepcion.ResourceNotFoundException;
import com.tesis.backendCuadernoDigital.repository.CuadroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CuadroService {

    @Autowired
    CuadroRepository cuadroRepository;

    public boolean guardarCuadro(Cuadro cuadro){
        return cuadroRepository.save(cuadro).getIdCuadro() !=null;
    }

    public Cuadro actualizarCuadro(Cuadro cuadro){
        return cuadroRepository.save(cuadro);
    }

    public Optional<Cuadro> findByIdCuadro(Long idCuadro){
        return cuadroRepository.findByIdCuadro(idCuadro);
    }

    public Optional<Cuadro> getByNumeroCuadro(String numeroCuadro){
        return cuadroRepository.findByNumeroCuadro(numeroCuadro);
    }

    public List<Cuadro> listarCuadros(){
        return cuadroRepository.findAllByOrderByNumeroCuadroAsc();
    }

    public List<Cuadro> getListadoCuadroDeUnaFinca(Long idFinca){
        return cuadroRepository.findByFinca_IdFinca(idFinca);
    }

    public boolean existsByNumeroCuadro(String numeroCuadro){
        return cuadroRepository.existsByNumeroCuadro(numeroCuadro);
    }

    public boolean existsByIDCuadro(Long id){
        return cuadroRepository.existsByIdCuadro(id);
    }


    // lo utilizo en la lista de los cuadros
    public Cuadro obtenerCuadro(String numeroCuadro){
        Cuadro cuadro = getByNumeroCuadro(numeroCuadro).orElseThrow(() -> new ResourceNotFoundException("El N° de Cuadro: "+numeroCuadro+" no Esiste"));
        return cuadro;
    }



    // lo utilizo en la lista de los cuadros en el controller de finca
    public  Cuadro getCuadro(Long id){
        Cuadro cuadro = findByIdCuadro(id).orElseThrow(()-> new ResourceNotFoundException("EL Club con ID: " + id + " no existe"));
        return cuadro;
    }


    public Integer getCantidadDeCuadros(){
        return cuadroRepository.countCuadroBy();
    }
}
