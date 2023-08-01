package com.tesis.backendCuadernoDigital.repository;

import com.tesis.backendCuadernoDigital.entity.AsesoriaAgroquimico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AsesoriaAgroquimicoRepository extends JpaRepository<AsesoriaAgroquimico, Long> {

    List<AsesoriaAgroquimico> findAllByOrderByIdAsc();
    Optional<AsesoriaAgroquimico> findById(Long id);
    List<AsesoriaAgroquimico> findByFinca_IdFinca(Long idFinca);
    Integer countAsesoriaAgroquimicoBy();
    Integer countAsesoriaAgroquimicoByAsesoriaAplicadaTrue();
    Integer countAsesoriaAgroquimicoByAsesoriaAplicadaFalse();
    boolean existsById(Long id);
    

}
