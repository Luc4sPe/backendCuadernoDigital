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



    // obtiene los riegos de un usuario por id
    List<Riego> findByNombreUsuario_Id (int id);

    // obtiene los riegos de un usuario por nombre de usuario
    List<Riego> findByNombreUsuario_NombreUsuario (String nombreUsuario);





    boolean existsById(int id);

    boolean existsByNumeroDeCuadro( int numeroCuadro);

    boolean existsByNombreUsuario(String nombreUsuario);
}
