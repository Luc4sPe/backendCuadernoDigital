package com.tesis.backendCuadernoDigital.repository;

import com.tesis.backendCuadernoDigital.entity.LaborSuelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LaborSueloRepository extends JpaRepository<LaborSuelo,Long> {

    List<LaborSuelo> findAllByOrderByLaborAsc();
    Optional<LaborSuelo> findById(Long id);
    Optional<LaborSuelo> findByLabor(String labor);
    List<LaborSuelo> findByIdCuadro_IdCuadro(Long idCuadro);
    boolean existsByLabor(String labor);
    boolean existsById(Long idLabor);
    Integer countLaborSueloBy();

}

