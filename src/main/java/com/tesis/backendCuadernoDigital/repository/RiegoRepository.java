package com.tesis.backendCuadernoDigital.repository;

import com.tesis.backendCuadernoDigital.entity.Riego;

import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RiegoRepository extends JpaRepository<Riego, Long> {

    List<Riego> findAllByOrderByIdAsc();
    Optional<Riego> findById(Long id);
    // obtiene los riegos de un usuario por id
  // List<Riego> findByIdCuadro_IdCuadro(Long idCuadro);
   List<Riego> findByFinca_IdFinca(Long idFinca);
    boolean existsById(Long id);
   // boolean existsByNumeroDeCuadro( Long numeroCuadro);
    Integer countRiegoBy();
}
