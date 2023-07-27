package com.tesis.backendCuadernoDigital.service;

import com.tesis.backendCuadernoDigital.entity.AsesoriaRiego;
import com.tesis.backendCuadernoDigital.excepcion.ResourceNotFoundException;
import com.tesis.backendCuadernoDigital.repository.AsesoriaRiegoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AsesoriaRiegoService {

    @Autowired
    AsesoriaRiegoRepository asesoriaRiegoRepository;

    public boolean guardarAsesoramientoRiego(AsesoriaRiego asesoriaRiego){
        return asesoriaRiegoRepository.save(asesoriaRiego).getId()!=null;
    }

    public AsesoriaRiego actualizarAsesoriaRiego(AsesoriaRiego asesoriaRiego){
        return asesoriaRiegoRepository.save(asesoriaRiego);
    }


    public Optional<AsesoriaRiego>getUnaAsesoriaRiego(Long id){
        return asesoriaRiegoRepository.findById(id);
    }

    public List<AsesoriaRiego> listarAsesoriaRiego(){
        return asesoriaRiegoRepository.findAllByOrderByIdAsc();
    }


    public List<AsesoriaRiego> getListadoAsesoriaRiegoDeUnaFincaPorId(Long idFinca){
        return asesoriaRiegoRepository.findByFinca_IdFinca(idFinca);
    }

    public boolean existeByIdAsesoriaRiego(Long id){
        return asesoriaRiegoRepository.existsById(id);
    }



    public Integer obtenerCantidadAsesoriasRiego(){
        return asesoriaRiegoRepository.countAsesoriaRiegoBy();
    }


    public int cantidadDeAplicacionAsesoria(){
        return asesoriaRiegoRepository.countAsesoriaRiegoByAsesoriaAplicadaTrue();
    }

    public int cantidadDeAsesoriaNoAplicada(){
        return asesoriaRiegoRepository.countAsesoriaRiegoByAsesoriaAplicadaFalse();
    }



    public AsesoriaRiego getAsesoriaRiego(Long id){
        AsesoriaRiego asesoriaRiego = getUnaAsesoriaRiego(id).orElseThrow(() -> new ResourceNotFoundException("El Asesoramiento de Riego con Id: " + id + "no existe"));
        return asesoriaRiego;
    }


    public void modificarEstado(Long id){
        AsesoriaRiego seAplico = asesoriaRiegoRepository.getById(id);
        seAplico.modificarEstado();
        asesoriaRiegoRepository.save(seAplico);

    }

}
