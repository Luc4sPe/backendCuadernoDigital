package com.tesis.backendCuadernoDigital.repository;

import com.tesis.backendCuadernoDigital.entity.TipoAgroquimico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipoAgroquimicoRepository extends JpaRepository<TipoAgroquimico,Long> {

    Optional<TipoAgroquimico> findByNombre(String nombre);
    List<TipoAgroquimico> findAllByOrderByIdAsc();
    // obtiene los Tipos de Agroquimico de un encargadoAgricola por nombre de usuario
    List<TipoAgroquimico> findByNombreEncargadoAgricola_NombreUsuario(String nombreUsuario);
    boolean existsByNombre(String nombre);
    boolean existsById(long id);
}
