package com.tesis.backendCuadernoDigital.repository;

import com.tesis.backendCuadernoDigital.entity.Plaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlagaRpository extends JpaRepository<Plaga,Long> {

    Optional<Plaga> findByNombre(String nombre);
    List<Plaga> findAllByOrderByIdAsc();

    // obtiene las plagas de un encargadoAgricola por nombre de usuario
     List<Plaga> findByNombreEncargadoAgricola_NombreUsuario(String nombreUsuario);

    boolean existsByNombre(String nombre);
    boolean existsById(long id);


}
