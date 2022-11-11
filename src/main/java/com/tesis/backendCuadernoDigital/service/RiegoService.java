package com.tesis.backendCuadernoDigital.service;

import com.tesis.backendCuadernoDigital.entity.Riego;
import com.tesis.backendCuadernoDigital.repository.RiegoRepository;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RiegoService {

    @Autowired
    RiegoRepository riegoRepository;

    public void guardarRiego(Riego riego){
        this.riegoRepository.save(riego);
    }
    public Riego modificarRiego(Riego riego){
        return riegoRepository.save(riego);
    }

    public Optional<Riego> getUnRiegoById(Long id){
        return riegoRepository.findById(id);
    }

    public List <Riego> listaRiegos(){
        return riegoRepository.findAllByOrderByIdAsc();
    }

   // public List<Riego> listadoRiegoPorCuadro(Long idCuadro){
     //   return riegoRepository.findByIdCuadro_IdCuadro(idCuadro);
    //}

    public List<Riego> getListadoRiegosDeUnaFincaPorId(Long idFinca){
        return riegoRepository.findByFinca_IdFinca(idFinca);
    }

    public boolean existsByIdRiego(Long id){
        return riegoRepository.existsById(id);
    }
    //public boolean existsByNumeroCuadro(Long numeroCuadro){
      //  return riegoRepository.existsByNumeroDeCuadro(numeroCuadro);
    //}

    public Integer obtenerCantidadRiegos(){
        return riegoRepository.countRiegoBy();
    }
}
