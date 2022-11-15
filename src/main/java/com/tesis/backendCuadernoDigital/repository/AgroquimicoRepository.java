package com.tesis.backendCuadernoDigital.repository;

import com.tesis.backendCuadernoDigital.entity.Agroquimico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgroquimicoRepository extends JpaRepository<Agroquimico,Long> {

    List<Agroquimico> findAllByOrderByIdAsc();
    Optional<Agroquimico> findByNombreComercial(String nombreComercial);

    Optional<Agroquimico> findById(Long id);
    boolean existsByNombreComercial(String nombreComercial);
    boolean existsByNumLote(String numLote);
    boolean existsById(Long id);
    Integer countAgroquimicoBy();

}


