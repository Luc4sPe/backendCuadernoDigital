package com.tesis.backendCuadernoDigital.service;

import com.tesis.backendCuadernoDigital.entity.Cultivo;
import com.tesis.backendCuadernoDigital.entity.Plantacion;
import com.tesis.backendCuadernoDigital.repository.PlagaRpository;
import com.tesis.backendCuadernoDigital.repository.PlantacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PlantacionService {

    @Autowired
    PlantacionRepository plantacionRepository;

    public boolean guardarPlantacion(Plantacion plantacion){
        return plantacionRepository.save(plantacion).getIdPlantacion() != null;
    }

    public Plantacion actualizarPlantacion(Plantacion plantacion){
        return plantacionRepository.save(plantacion);
    }

    public Optional<Plantacion> getPlantacion(Long idPlantacion){
        return plantacionRepository.findByIdPlantacion(idPlantacion);
    }

    public Optional<Plantacion> getByJustificacion(String justificacion){
        return  plantacionRepository.findByJustificacion(justificacion);
    }

    public List<Plantacion> ListarPlantacion(){
        return plantacionRepository.findAllByOrderByNombreTipoCultivoAsc();
    }


     public List<Plantacion> listadoPlantacionDeUnCultivoPorNombre(String nombreCultivo){
        return plantacionRepository.findByNombreTipoCultivo_Nombre(nombreCultivo);
     }


    public List<Plantacion> listadoPlantacionDeUnCultivoPorId(Long idCultivo){
        return plantacionRepository.findByNombreTipoCultivo_IdCultivo(idCultivo);
    }

    public boolean existsByIdPlantacion(Long id){
        return plantacionRepository.existsByIdPlantacion(id);
    }
    public boolean existsByJustificacion(String justificacion){
        return plantacionRepository.existsByJustificacion(justificacion);
    }

    public Integer getCantidadDePlantacion(){
        return plantacionRepository.countPlantacionBy();
    }




}
