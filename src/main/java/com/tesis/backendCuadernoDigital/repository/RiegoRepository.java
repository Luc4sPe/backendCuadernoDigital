package com.tesis.backendCuadernoDigital.repository;

import com.tesis.backendCuadernoDigital.entity.Riego;

import com.tesis.backendCuadernoDigital.security.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RiegoRepository extends JpaRepository<Riego, Integer> {

    Optional<Riego> findById(int id);

    //List<Riego> findByNombreUsuarioOrderByNombreUsuarioAs(String nombreUsuario);
    List<Riego> findAllByOrderByIdAsc();

    boolean existsById(int id);

    boolean existsByNumeroDeCuadro( int numeroCuadro);

    boolean existsByNombreUsuario(String nombreUsuario);
}
