package com.tesis.backendCuadernoDigital.repository;

import com.tesis.backendCuadernoDigital.entity.Agroquimico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgroquimicoRepository extends JpaRepository<Agroquimico,Long> {

    Optional<Agroquimico> findByNombreComun(String nombreComun);
    List<Agroquimico> findAllByOrderByIdAsc();
    // obtiene los Agroquimico de un encargadoAgricola por nombre de usuario
    List<Agroquimico> findByNombreEncargadoAgricola_NombreUsuario(String nombreUsuario);
    boolean existsByNombreComun(String nombreComun);
    boolean existsById(long id);
}
