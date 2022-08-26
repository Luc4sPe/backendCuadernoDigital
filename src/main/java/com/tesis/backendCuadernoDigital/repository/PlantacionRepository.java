package com.tesis.backendCuadernoDigital.repository;

import com.tesis.backendCuadernoDigital.entity.Plantacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlantacionRepository extends JpaRepository<Plantacion, Long> {

    List<Plantacion> findAllByOrderByIdPlantacionAsc();
    List<Plantacion> findAllByOrderByNombreTipoCultivoAsc();
    //obtener las plantaciones de un cultivo por nombre de cultivo
    List<Plantacion> findByNombreTipoCultivo_Nombre(String nombre);
    List<Plantacion> findByNombreTipoCultivo_IdCultivo(Long id);
    Optional<Plantacion> findByIdPlantacion(Long id);
    Optional<Plantacion> findByNombreTipoCultivo(String nombre);
    Optional<Plantacion> findByJustificacion(String justificacion);
    boolean existsByIdPlantacion(Long id);
    boolean existsByNombreTipoCultivo(String nombreCultivo);
    boolean existsByJustificacion(String justificacion);
    Integer countPlantacionBy();

}
