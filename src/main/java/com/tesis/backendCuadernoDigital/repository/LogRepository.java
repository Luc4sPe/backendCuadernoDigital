package com.tesis.backendCuadernoDigital.repository;

import com.tesis.backendCuadernoDigital.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {

    List<Log> findByUsuario_IdOrderByFechaAsc(int id);

    List<Log> findByUsuario_IdOrderByFechaDesc(int id);

    List<Log> findAllByOrderByFechaDesc();

    List<Log> findByUsuario_NombreUsuarioOrderByFechaDesc(String nombreUsuario);

    Integer countByUsuario_Id(int id);

}
