package com.tesis.backendCuadernoDigital.repository;

import com.tesis.backendCuadernoDigital.entity.AsesoriaRiego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AsesoriaRiegoRepository extends JpaRepository<AsesoriaRiego, Long> {

    List<AsesoriaRiego> findAllByOrderByIdAsc();
    Optional<AsesoriaRiego> findById(Long id );
     List<AsesoriaRiego> findByFinca_IdFinca(Long idFinca);
     Integer countAsesoriaRiegoBy();
     Integer countAsesoriaRiegoByAsesoriaAplicadaTrue();
     Integer countAsesoriaRiegoByAsesoriaAplicadaFalse();
     boolean existsById(Long id);



}
