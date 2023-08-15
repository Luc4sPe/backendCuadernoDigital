package com.tesis.backendCuadernoDigital.service;

import com.tesis.backendCuadernoDigital.entity.AsesoriaAgroquimico;
import com.tesis.backendCuadernoDigital.excepcion.ResourceNotFoundException;
import com.tesis.backendCuadernoDigital.repository.AsesoriaAgroquimicoRepository;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AsesoriaAgroquimicoService {

    @Autowired
    AsesoriaAgroquimicoRepository asesoriaAgroquimicoRepository;

    public boolean guardarAsesoramientoAgroquimico(AsesoriaAgroquimico asesoriaAgroquimico){
        return asesoriaAgroquimicoRepository.save(asesoriaAgroquimico).getId()!=null;
    }

    public AsesoriaAgroquimico actualizarAsesoriaAgroquimico(AsesoriaAgroquimico asesoriaAgroquimico){
        return asesoriaAgroquimicoRepository.save(asesoriaAgroquimico);
    }

    public Optional<AsesoriaAgroquimico> getUnaAsesoriaAgroquimico(Long id){
        return asesoriaAgroquimicoRepository.findById(id);
    }

    public List<AsesoriaAgroquimico> listarAsesoriaAgroquimico(){
        return asesoriaAgroquimicoRepository.findAllByOrderByFechaEstimadaAplicacionAsc();
    }


    public List<AsesoriaAgroquimico> getListadoAsesoriaAgroqimicoDeUnaFincaPorId(Long idFinca){
        return asesoriaAgroquimicoRepository.findByFinca_IdFinca(idFinca);
    }

    public boolean existeByIdAsesoriaAgroquimico(Long id){
        return asesoriaAgroquimicoRepository.existsById(id);
    }



    public Integer obtenerCantidadAsesoriasAgroquimico() {
        return asesoriaAgroquimicoRepository.countAsesoriaAgroquimicoBy();
    }


    public int cantidadDeAplicacionAsesoria(){
        return asesoriaAgroquimicoRepository.countAsesoriaAgroquimicoByAsesoriaAplicadaTrue();
    }

    public int cantidadDeAsesoriaNoAplicada(){
        return asesoriaAgroquimicoRepository.countAsesoriaAgroquimicoByAsesoriaAplicadaFalse();
    }

    public Integer obtenerTotalAsesoriaByProductor(Optional<Usuario>  nombre){
        return asesoriaAgroquimicoRepository.countAsesoriaAgroquimicoByProductor(nombre);
    }

    public Integer obtenerTotalAsesoriaAplicadaByProductor(Optional<Usuario>  nombre){
        return asesoriaAgroquimicoRepository.countAsesoriaAgroquimicoByAsesoriaAplicadaTrueAndProductor(nombre);
    }

    public Integer obtenerTotalAsesoriaNoAplicadaByProductor(Optional<Usuario>  nombre){
        return asesoriaAgroquimicoRepository.countAsesoriaAgroquimicoByAsesoriaAplicadaFalseAndProductor(nombre);
    }

    public AsesoriaAgroquimico getAsesoriaAgroquimico(Long id){
        AsesoriaAgroquimico asesoriaAgroquimico = getUnaAsesoriaAgroquimico(id).orElseThrow(()-> new ResourceNotFoundException("El asesoramiento de riego con Id: "+ id +" no existe") );
        return asesoriaAgroquimico;
    }


    public void modificarEstado(Long id){

        AsesoriaAgroquimico seAplico = asesoriaAgroquimicoRepository.getById(id);
        seAplico.modificarEstado();
        asesoriaAgroquimicoRepository.save(seAplico);

    }

}
