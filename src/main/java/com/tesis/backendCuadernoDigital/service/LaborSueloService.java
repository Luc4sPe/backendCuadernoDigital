package com.tesis.backendCuadernoDigital.service;

import com.tesis.backendCuadernoDigital.entity.LaborSuelo;
import com.tesis.backendCuadernoDigital.repository.LaborSueloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LaborSueloService {

    @Autowired
    LaborSueloRepository laborSueloRepository;

    public void guardarLaborSuelo(LaborSuelo laborSuelo){
         laborSueloRepository.save(laborSuelo);
    }

    public LaborSuelo modificarLabor(LaborSuelo laborSuelo){
      return laborSueloRepository.save(laborSuelo);
    }

    public Optional<LaborSuelo> getLaborSuelo(Long id){
        return laborSueloRepository.findById(id);
    }

    public Optional<LaborSuelo> getLaborSuelo(String labor){
        return laborSueloRepository.findByLabor(labor);
    }

    public List<LaborSuelo> listarLabores(){
        return laborSueloRepository.findAllByOrderByLaborAsc();
    }

    public List<LaborSuelo> listadoLaborPorCuadro(Long idCuadro){
        return laborSueloRepository.findByIdCuadro_IdCuadro(idCuadro);
    }

    public boolean existsByLaborSuelo(String labor){
        return laborSueloRepository.existsByLabor(labor);
    }

    public boolean existsByIdLabor(Long idLabor){
        return laborSueloRepository.existsById(idLabor);
    }

    public Integer obtenerCantidadLaborSuelo(){
        return laborSueloRepository.countLaborSueloBy();
    }
}

