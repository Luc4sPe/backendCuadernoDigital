package com.tesis.backendCuadernoDigital.repository;

import com.tesis.backendCuadernoDigital.entity.Cultivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CultivoRepository extends JpaRepository<Cultivo, Long> {
    List<Cultivo> findAllByOrderByIdCultivoAsc();
    List<Cultivo> findAllByOrderByNombre();
    Optional<Cultivo> findByNombre(String nombre);
    Optional<Cultivo> findByIdCultivo(Long idCultivo);
    boolean existsByNombre(String nombre);
    boolean existsByRemito(String remito);
    Integer countCultivoBy();
}
