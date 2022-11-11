package com.tesis.backendCuadernoDigital.repository;


import com.tesis.backendCuadernoDigital.entity.Cuadro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuadroRepository extends JpaRepository<Cuadro, Long> {

    List<Cuadro> findAllByOrderByNumeroCuadroAsc();
    List<Cuadro> findByFinca_IdFinca(Long id);
    List<Cuadro> findCuadroByFinca_IdFinca(Long id);
    Optional<Cuadro> findByIdCuadro(Long id);
    Optional<Cuadro> findByNumeroCuadro(String numeroCuadro);
    Optional<Cuadro> findByCultivoAnterior(String cultivoAnterior);
    boolean existsByNumeroCuadro(String numeroCuadro);
    boolean existsByIdCuadro(Long id);
    Integer countCuadroBy();


}
