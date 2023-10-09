package com.tesis.backendCuadernoDigital.repository;

import com.tesis.backendCuadernoDigital.entity.AsesoriaAgroquimico;
import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AsesoriaAgroquimicoRepository extends JpaRepository<AsesoriaAgroquimico, Long> {

    List<AsesoriaAgroquimico> findAllByOrderByFechaEstimadaAplicacionAsc();

    Optional<AsesoriaAgroquimico> findById(Long id);
    List<AsesoriaAgroquimico> findByFinca_IdFinca(Long idFinca);
    List<AsesoriaAgroquimico> findByProductor_NombreUsuario(String nombre);
    Integer countAsesoriaAgroquimicoBy();
    Integer countAsesoriaAgroquimicoByAsesoriaAplicadaTrue();
    Integer countAsesoriaAgroquimicoByAsesoriaAplicadaFalse();
    Integer countAsesoriaAgroquimicoByAsesoriaAplicadaTrueAndProductor(Optional<Usuario>  nombre);
    Integer countAsesoriaAgroquimicoByAsesoriaAplicadaFalseAndProductor(Optional<Usuario>  nombre);
    Integer countAsesoriaAgroquimicoByProductor(Optional<Usuario>  nombre);
    boolean existsById(Long id);


}
