package com.tesis.backendCuadernoDigital.service;

import com.tesis.backendCuadernoDigital.entity.Cultivo;
import com.tesis.backendCuadernoDigital.repository.CultivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CultivoService {

    @Autowired
    CultivoRepository cultivoRepository;

    public boolean guardarCultivo(Cultivo cultivo){
        return cultivoRepository.save(cultivo).getIdCultivo()!= null;
    }

    public Cultivo actualizarCultivo(Cultivo cultivo){
        return cultivoRepository.save(cultivo);
    }

    public Optional<Cultivo> getUnCultivo(Long idCultivo){
        return cultivoRepository.findById(idCultivo);
    }

    public Optional<Cultivo> getByNombre(String nombre){
        return  cultivoRepository.findByNombre(nombre);
    }

    public List<Cultivo> listarCultivos(){
        return cultivoRepository.findAllByOrderByIdCultivoAsc();
    }

    public List<Cultivo> listadoCultivoPorNombre(){
        return cultivoRepository.findAllByOrderByNombre();
    }

    public boolean existByNombre(String nombre){
        return cultivoRepository.existsByNombre(nombre);
    }
    public boolean existByRemito(String remito){
        return cultivoRepository.existsByRemito(remito);
    }

    public boolean existsByIdCultivo(Long id){
        return cultivoRepository.existsById(id);
    }

    public  Integer getCantidadDeCultivos(){
        return cultivoRepository.countCultivoBy();
    }



}