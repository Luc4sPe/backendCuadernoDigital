package com.tesis.backendCuadernoDigital.service;

import com.tesis.backendCuadernoDigital.entity.AplicacionDeAgroquimico;
import com.tesis.backendCuadernoDigital.excepcion.ResourceNotFoundException;
import com.tesis.backendCuadernoDigital.repository.AplicacionAgroquimicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AplicacionAgroquimicoService {

    @Autowired
    AplicacionAgroquimicoRepository aplicacionAgroquimicoRepository;

    public  boolean guardarAplicaAgroquimico(AplicacionDeAgroquimico agroquimico){
        return  aplicacionAgroquimicoRepository.save(agroquimico).getId()!= null;
    }

    public  AplicacionDeAgroquimico actualizarAplicaAgroquimico(AplicacionDeAgroquimico agroquimico){
        return aplicacionAgroquimicoRepository.save(agroquimico);
    }


   // public Optional<AplicacionDeAgroquimico>getUnAplicacionAgroquimco(Long id){
     //   return aplicacionAgroquimicoRepository.findById(id);
    //}

    public Optional<AplicacionDeAgroquimico> getUnAplicacionAgroquimico(Long id){
        return aplicacionAgroquimicoRepository.findById(id);
    }







    public List<AplicacionDeAgroquimico> listarAplicacionAgroquimico(){
        return aplicacionAgroquimicoRepository.findAllByOrderByIdAsc();
    }


    public List<AplicacionDeAgroquimico> getListadoAplicacionAgroDeUnaFincaPorId(Long idFinca){
        return aplicacionAgroquimicoRepository.findByFinca_IdFinca(idFinca);
    }

    public List<AplicacionDeAgroquimico> getListadoAplicacionAgroDeUnaFincaPorNombre(String nombre){
        return aplicacionAgroquimicoRepository.findByFinca_Nombre(nombre);
    }

    public void borrar(Long id){
        aplicacionAgroquimicoRepository.deleteById(id);
    }

    public boolean existeById(Long id){
        return aplicacionAgroquimicoRepository.existsById(id);
    }


    public  AplicacionDeAgroquimico getAplicaAgroquimico(Long id){
        AplicacionDeAgroquimico agroquimico= getUnAplicacionAgroquimico(id).orElseThrow(()-> new ResourceNotFoundException("EL agroquimico con ID: " + id + " no existe"));
        return agroquimico;
    }
}


