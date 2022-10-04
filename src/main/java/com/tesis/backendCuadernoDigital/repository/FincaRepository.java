package com.tesis.backendCuadernoDigital.repository;

import com.tesis.backendCuadernoDigital.entity.Finca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FincaRepository extends JpaRepository<Finca, Long> {

    List<Finca> findAllByOrderByNombreAsc();
    // obtiene las fincas de un usuario por nombre de usuario

    List<Finca> findByProductor_NombreUsuario(String nombreUsuario);

    Optional<Finca> findByIdFinca(Long id);
    Optional<Finca> findByNombre(String nombreFinca);
    boolean existsByNombre(String nombreFinca);
    boolean existsByIdFinca(Long id);
    Integer countFincaBy();




}
