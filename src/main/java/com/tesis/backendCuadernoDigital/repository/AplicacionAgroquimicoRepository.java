package com.tesis.backendCuadernoDigital.repository;


import com.tesis.backendCuadernoDigital.entity.AplicacionDeAgroquimico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AplicacionAgroquimicoRepository extends JpaRepository<AplicacionDeAgroquimico,Long> {


    Optional<AplicacionDeAgroquimico> findById(long id);
    List<AplicacionDeAgroquimico> findAllByOrderByIdAsc();
    // obtiene la aplicacion de los agroquimicos de un encargadoAgricola por nombre de usuario
    List<AplicacionDeAgroquimico> findByNombreUsuario_NombreUsuario(String nombreUsuario);
    boolean existsById(long id);
    boolean existsByNumeroCuadro(int numeroCuadro);
}
