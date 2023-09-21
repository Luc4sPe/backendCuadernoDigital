package com.tesis.backendCuadernoDigital.repository;


import com.tesis.backendCuadernoDigital.entity.AplicacionDeAgroquimico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AplicacionAgroquimicoRepository extends JpaRepository<AplicacionDeAgroquimico,Long> {


    List<AplicacionDeAgroquimico> findAllByOrderByIdAsc();

    //Optional<AplicacionDeAgroquimico> findById(Long id);
    Optional<AplicacionDeAgroquimico> findById(Long id);

    //Optional<AplicacionDeAgroquimico> findByJustificacion(String justificacion);

    List<AplicacionDeAgroquimico> findByFinca_IdFinca(Long idFinca);
    List<AplicacionDeAgroquimico> findByFinca_Nombre(String nombre);

    List<AplicacionDeAgroquimico> findByFinca_Productor_NombreUsuario(String nombre);




    boolean existsById(Long id);
    boolean existsByJustificacion(String justificacion);
    Integer countAplicacionDeAgroquimicoBy();


}


